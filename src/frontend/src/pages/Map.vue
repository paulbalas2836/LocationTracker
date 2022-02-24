<template>
  <div class="relative w-screen h-screen overflow-hidden">
  <div class="flex text-center flex-row absolute z-20" style="height: 10vh">
  <navigation-bar ceva="ceva"/>
    <div class="justify-content-center flex-col flex">
      <c-button class="mt-56 ml-1 text-neutral-500" color="bg-blue-500" ring="focus:ring-blue-400" hover="hover:bg-blue-300" @click="saveLocation()">Save Current Location</c-button>
      <c-button class="mt-8 ml-1 text-neutral-500"  color="bg-blue-500" ring="focus:ring-blue-400" hover="hover:bg-blue-300" @click="openLocationBetweenModal = true">Show Specified Locations</c-button>
      <c-button class="mt-8 ml-1 text-neutral-500" color="bg-blue-500" ring="focus:ring-blue-400" hover="hover:bg-blue-300" @click="moveTo" >Move To Current Location</c-button>
      <c-button class="mt-8 ml-1 text-neutral-500" color="bg-blue-500" ring="focus:ring-blue-400" hover="hover:bg-blue-300" @click="clearMap" >Clear all marks</c-button>
    </div>


    <c-modal v-if="openLocationBetweenModal === true">
      <template v-slot:header>
          <h3 class="text-lg leading-6 font-medium text-gray-900 text-center">
            Show Marks Between Dates
          </h3>
      </template>
      <template v-slot:default>
        <div class="w-full flex">
          <div  class="mr-2 mt-2  flex-row justify-center w-1/2">
            <c-label for="startDate" value="Start Date" class="ml-12"/>
            <c-input id="startDate" type="date" class="mt-1 block w-full"  v-model="form.startDate" />
          </div>
          <div class="ml-2 mt-2 flex-row justify-center w-1/2">
            <c-label for="endDate" value="End Date" class="ml-12"/>
            <c-input id="endDate" type="date" class="mt-1 block w-full "  v-model="form.endDate"/>
          </div>
        </div>
        <div v-if="loggedUser.appUserRole === 'ADMIN'" class="ml-10 mt-2 flex-row justify-center w-3/4">
          <c-label for="terminalId" value="Terminal Id" class="ml-20"/>
          <c-input id="terminalId" class="mt-1 block w-full "  v-model="form.terminalId"/>
        </div>
      </template>
      <template v-slot:footer>
        <div class="mt-5 sm:mt-6 flex flex-row justify-center">
          <c-button color="bg-red-500" hover="hover:bg-red-600" ring="focus:ring-red-500" class="mr-5" @click="openLocationBetweenModal = false">
            Cancel
          </c-button>
          <c-button @click="getMarkers">
            Save
          </c-button>
        </div>
      </template>
    </c-modal>

    <c-modal v-if="openDeleteLocationModal === true">
      <template v-slot:header>
        <h3 class="text-lg leading-6 font-medium text-gray-900 text-center">
          Delete Current Location
        </h3>
      </template>
      <template v-slot:default>
          <div  class="mr-2 mt-2">
            Are you sure you want to delete this location?
        </div>
      </template>
      <template v-slot:footer>
        <div class="mt-5 sm:mt-6 flex flex-row justify-center">
          <c-button color="bg-red-500" hover="hover:bg-red-600" ring="focus:ring-red-500" class="mr-5" @click="openDeleteLocationModal = false">
            Cancel
          </c-button>
          <c-button @click="deleteMarker">
            Delete
          </c-button>
        </div>
      </template>
    </c-modal>


    <c-modal v-if="openUpdateLocationModal === true">
      <template v-slot:header>
        <h3 class="text-lg leading-6 font-medium text-gray-900 text-center">
          Update Current Location
        </h3>
      </template>
      <template v-slot:default>
        <div  class="mr-2 mt-2 flex">
          <div  class="mr-2 mt-2  flex-row justify-center w-1/2">
            <c-label for="latitude" value="Latitude"/>
            <c-input id="latitude" type="number" class="mt-1 block w-full" v-model="updateForm.latitude" />
          </div>
          <div class="ml-2 mt-2 flex-row justify-center w-1/2">
            <c-label for="longitude" value="Longitude" />
            <c-input id="longitude" type="number"  class="mt-1 block w-full" v-model="updateForm.longitude"/>
          </div>
        </div>
      </template>
      <template v-slot:footer>
        <div class="mt-5 sm:mt-6 flex flex-row justify-center">
          <c-button color="bg-red-500" hover="hover:bg-red-600" ring="focus:ring-red-500" class="mr-5" @click="openUpdateLocationModal = false">
            Cancel
          </c-button>
          <c-button @click="updateMarker">
            Update
          </c-button>
        </div>
      </template>
    </c-modal>


  </div>
  <div ref="mapDiv" style="width: 100%; height: 91vh" class="mt-16"/>
  </div>
</template>

<script>
/* eslint-disable no-undef */
import CLabel from "@/components/Label"
import CInput from "@/components/Input"
import {computed, ref, onMounted, reactive} from 'vue'
import { useGeolocation } from './useGeolocation'
import { Loader } from '@googlemaps/js-api-loader'
import NavigationBar from "@/components/NavigationBar";
import axios from "axios";
import CButton from '@/components/Button'
import CModal from "@/components/Modal";
const GOOGLE_MAPS_API_KEY = 'AIzaSyBOtooU2tOEPlOHsklPAvtyIglvNuu-Tgs'
const POSITION_URL = 'http://localhost:8080/position/'
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
export default {
  name: 'App',
  components: {NavigationBar, CButton, CModal, CInput, CLabel},
  setup() {
    const { coords } = useGeolocation()
    const openLocationBetweenModal = ref(false)
    const openDeleteLocationModal = ref(false)
    const openUpdateLocationModal = ref(false)
    const updateForm = reactive({latitude: '', longitude: ''})
    const markers = reactive([])
    const currPos = computed(() => ({
      lat: coords.value.latitude,
      lng: coords.value.longitude
    }))
    const selectedMarker = reactive({positionId: null, currentMarkerId: null})
    const positionIdDelete = ref(null)
    const loader = new Loader({ apiKey: GOOGLE_MAPS_API_KEY })
    const mapDiv = ref(null)
    const loggedUser = computed(()=>{
      return cookies.get('token').appUserDto
    })
    const form = reactive({startDate: '', endDate: '', terminalId: loggedUser.value.appUserRole === "ADMIN" ? "" : loggedUser.value.email})
    let map = ref(null)
    onMounted(async () => {
      await loader.load()
      map.value = new google.maps.Map(mapDiv.value, {
        center: currPos.value,
        zoom: 7
      })
      new google.maps.Marker({
        position: currPos.value,
        map: map.value,
        title: "Current Location",
        icon:'http://maps.google.com/mapfiles/ms/icons/blue-dot.png',
      })
    })

    return {updateForm, openUpdateLocationModal, loggedUser, currPos, mapDiv, openLocationBetweenModal, form, map, openDeleteLocationModal, markers,positionIdDelete, selectedMarker }
  },
  methods:{
    updateMarker: function(){
      this.axios.put(POSITION_URL + 'updatePosition/' + this.selectedMarker.positionId,{
        latitude: this.updateForm.latitude, longitude: this.updateForm.longitude
      }, {
        headers: {
          Authorization: "Bearer " + this.$cookies.get('token').jwt
        }
      }).then(() =>{
        this.openUpdateLocationModal = false
          this.$swal.fire({
            toast: true,
            icon: 'success',
            title: "Location has been updated",
            position: 'bottom-end',
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
          })
      }).catch(err => {
        this.$swal.fire({
          toast: true,
          icon: 'error',
          title: err.response.data.errors[0],
          position: 'bottom-end',
          showConfirmButton: false,
          timer: 2000,
          timerProgressBar: true,
        })
      })
    },
    deleteMarker: function (){
      this.markers[this.selectedMarker.currentMarkerId].setMap(null)
      this.markers[this.selectedMarker.currentMarkerId].setVisible(false)
      this.axios.delete(POSITION_URL + 'deletePosition/' + this.selectedMarker.positionId, {
        headers: {
          Authorization: "Bearer " + this.$cookies.get('token').jwt
        }
      }).then(() =>{
        this.openDeleteLocationModal = false
        this.$swal.fire({
          toast: true,
          icon: 'success',
          title: "Location Deleted",
          position: 'bottom-end',
          showConfirmButton: false,
          timer: 2000,
          timerProgressBar: true,
        })
      })
    },
    moveTo: function(){
      this.map.panTo( new google.maps.LatLng( this.currPos.lat, this.currPos.lng ) );
    },
    getDates: function (){
      let dates = []
      let startDate = new Date(this.form.startDate)
      let startDD = String(startDate. getDate()). padStart(2, '0');
      let startMM = String(startDate. getMonth() + 1). padStart(2, '0');
      let startYYYY = startDate. getFullYear();
      let newStartDate= startYYYY + '-' + startMM + '-' + startDD + 'T' + '00:00:00.000'
      dates.push(newStartDate)

      let endDate = new Date(this.form.endDate)
      let endDD = String(endDate. getDate()). padStart(2, '0');
      let endMM = String(endDate. getMonth() + 1). padStart(2, '0');
      let endYYYY = endDate. getFullYear();
      let newEndDate = endYYYY + '-' + endMM + '-' + endDD + 'T' + '23:59:59.000'
      dates.push(newEndDate)

      return dates
    },
    getMarkers: function (){
     let dates = this.getDates()
      axios.post(POSITION_URL + 'getPositionsByDate',{
        startDate: dates[0],
        endDate: dates[1],
        terminalId: this.form.terminalId,
      },{
        headers: {
          Authorization: "Bearer " + cookies.get('token').jwt
        }
      }).then(res => {
        this.clearMap()
        for(let i=0; i < res.data.length; i++) {
          this.markers.push(new google.maps.Marker({
            position: new google.maps.LatLng(res.data[i].latitude, res.data[i].longitude),
            map: this.map,
          }))
          if (this.loggedUser.appUserRole === "ADMIN") {
            this.markers[i].addListener('click', this.openDeleteModal.bind(this, i, res.data[i].id), false)
            this.markers[i].addListener('contextmenu', this.openUpdateModal.bind(this, i, res.data[i].id), false)
          }
        }

        this.openLocationBetweenModal = false

        this.$swal.fire({
          toast: true,
          icon: 'success',
          title: "Locations marked",
          position: 'bottom-end',
          showConfirmButton: false,
          timer: 2000,
          timerProgressBar: true,
        })
      })
    },
    openUpdateModal: function(i, id){
      this.openUpdateLocationModal = true
      this.selectedMarker.currentMarkerId = i
      this.selectedMarker.positionId = id
    },
    openDeleteModal: function(i, id){
      this.openDeleteLocationModal = true
      this.selectedMarker.currentMarkerId = i
      this.selectedMarker.positionId = id
    },
    saveLocation: function (){
      axios.post(POSITION_URL + 'createPosition',{
        latitude: this.currPos.lat,
        longitude: this.currPos.lng,
        terminalId: this.loggedUser.email
      },{
        headers: {
          Authorization: "Bearer " + cookies.get('token').jwt
        }

      }).then(() =>{
        this.$swal.fire({
          toast: true,
          icon: 'success',
          title: "Location Saved",
          position: 'bottom-end',
          showConfirmButton: false,
          timer: 2000,
          timerProgressBar: true,
        })
      }).catch(err => {
        console.log(err.toString())
      })
    },
    clearMap: function() {
      if(this.markers.length){
        for(let i = 0; i < this.markers.length; i++){
          this.markers[i].setMap(null)
          this.markers[i].setVisible(false)
        }
        this.markers = []
      }
    }
  }
}
</script>

