
<template>
  <div class="bg-white shadow sm:rounded-lg mt-10">
    <div class="sm:p-4">
  <div class="sm:grid sm:grid-cols-3 sm:gap-4 sm:items-start sm:border-t sm:border-gray-200 sm:pt-5">
    <c-label value="Upload Profile Picture" class="text-xl" />
    <div class="mt-1 sm:mt-0 sm:col-span-2">
      <div class="max-w-lg flex justify-center px-6 pt-5 pb-6 border-2 border-gray-300 border-dashed rounded-md">
        <div class="space-y-1 text-center">
          <div class="mt-2 ml-14">
            <img :src="loggedUser.image" alt="" class="rounded-full h-20 w-20 object-cover">
          </div>
          <div class="flex text-sm text-gray-600">
            <label for="file-upload" class="relative cursor-pointer bg-white rounded-md font-medium text-indigo-600 hover:text-indigo-500 focus-within:outline-none focus-within:ring-2 focus-within:ring-offset-2 focus-within:ring-indigo-500">
              <span>Upload a file</span>
              <input id="file-upload" name="file-upload" type="file" class="sr-only" @change="onFileSelected"/>
            </label>
            <p class="pl-1">or drag and drop</p>
          </div>
          <p class="text-xs text-gray-500">
            PNG, JPG, GIF up to 10MB
          </p>
        </div>
      </div>
    </div>
  </div>
        <div class="pt-5">
          <div class="flex justify-end">
            <c-button @click="onUpload" >
              Upload
            </c-button>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
import CButton from '@/components/Button'
import CLabel from '@/components/Label'
import {computed, ref} from 'vue'
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
const USER_URL = 'http://localhost:8080/user/'
export default {
  name: "ChangeImage",
  components:{CButton, CLabel},

  setup(){
    const formData = new FormData()
    let image = ref(null)
    const loggedUser = computed(() =>{
      return cookies.get('token').appUserDto
    })
    return{
      formData,
      image,
      loggedUser
    }
  },

  methods:{
    onFileSelected: function (event){
      this.formData.append("image",event.target.files[0])
    },
    onUpload: function (){
      this.axios.put(USER_URL + 'changeImageUser/' + this.loggedUser.email, this.formData,
      {
        headers: {
          Authorization: "Bearer " + cookies.get('token').jwt
        }
          }).then(()=>{
        this.$swal.fire({
          toast: true,
          icon: 'success',
          title: "Profile Image Set",
          position: 'bottom-end',
          showConfirmButton: false,
          timer: 2000,
          timerProgressBar: true,
        })
      }).catch(err=>{
        console.log(err.toString())
      })
    },
  },
}
</script>
