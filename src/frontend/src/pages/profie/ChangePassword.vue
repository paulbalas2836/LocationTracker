<template>
  <div class="bg-white shadow sm:rounded-lg">
    <div class="px-4 py-5 sm:p-4">
      <div class="sm:grid sm:grid-cols-3 sm:gap-4 sm:items-start sm:border-t sm:border-gray-200 sm:pt-5">
        <c-label value="Change Password" class="text-xl" />
      </div>
      <form class="mt-1 sm:mt-0 sm:col-span-2 flex justify-center flex-col">
        <div class="mt-5">
        <c-label value="Current Password" for="currentPassword"/>
        <c-input type="password" id="currentPassword" v-model="form.currentPassword"/>
        </div>

        <div class="mt-5">
        <c-label value="New Password" for="newPassword"/>
        <c-input type="password" id="newPassword" v-model="form.newPassword"/>
        </div>

        <div class="mt-5">
        <c-label value="Verify New Password" for="verifyNewPassword"/>
        <c-input type="password" id="verifyNewPassword" v-model="form.newMatchingPassword"/>
        </div>
      </form>
      <div class="pt-5">
        <div class="flex justify-end">
          <c-button @click="submit">
            Save
          </c-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CInput from '@/components/Input'
import CLabel from '@/components/Label'
import CButton from '@/components/Button'
import {computed, reactive} from "vue";
import { useCookies } from "vue3-cookies";
const { cookies } = useCookies();
const USER_URL = 'http://localhost:8080/user/'
export default {
  name: "ChangePassword",
  components:{CInput, CLabel, CButton},
  setup(){
    const form = reactive({currentPassword: '', newPassword:'', newMatchingPassword:''})
    const loggedUser = computed(()=>{
      return cookies.get('token').appUserDto
    })
    return{
      form,
      loggedUser
    }
  },
  methods:{
    submit: function (){
        this.axios.put(USER_URL + 'changePasswordUser/' + this.loggedUser.email,{
          oldPassword: this.form.currentPassword,
          newPassword: this.form.newPassword,
          newMatchingPassword: this.form.newMatchingPassword
        },{
          headers:{
            Authorization: "Bearer " + cookies.get('token').jwt
          }
        }).then(()=>{
          this.$swal.fire({
            toast: true,
            icon: 'success',
            title: "Password Updated",
            position: 'bottom-end',
            showConfirmButton: false,
            timer: 2000,
            timerProgressBar: true,
          })
        }).catch(err =>{
          console.log(err.toString())
        })

    }
  }
}
</script>
