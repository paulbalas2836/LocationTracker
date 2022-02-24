<template>
  <div  class="fixed top-0 left-0 w-screen h-16 m-0 flex flex-col bg-blue-500 text-white shadow-lg">
      <div class="flex-shrink-0 border-t border-blue-gray-200 p-4 flex justify-end">
        <router-link to="/" class="mr-auto">
        <div>
          MAP
        </div>
        </router-link>
        <dropdown class="ml-3 relative">
          <template v-slot:toggler>
            <button  class="max-w-xs bg-white flex items-center text-sm rounded-full focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
              <span class="sr-only">Open user menu</span>
              <img  class=" w-12 rounded-full" :src="loggedUser.image" alt="" />
            </button>
          </template>
            <dropdown-content class="origin-top-right absolute">
              <router-link :to="userNavigation.profile.href">
              <dropdown-items>
                 {{userNavigation.profile.name}}
              </dropdown-items>
              </router-link>
              <dropdown-items @click="logout">
                {{userNavigation.logout.name}}
              </dropdown-items>
            </dropdown-content>
        </dropdown>

      </div>

  </div>
</template>


<script>
import Dropdown from "@/components/Dropdown";
import DropdownContent from "@/components/DropdownContent";
import DropdownItems from "@/components/DropdownItems";
import {useRouter} from "vue-router";
import {reactive, ref} from "vue"
const userNavigation = {
  profile: {name: 'Your Profile', href: 'Profile'},
  logout: {name: 'Sign out', href: '#'},
  }
export default {
  name: "NavigationBar",
  props:['ceva'],
  components: {DropdownItems, DropdownContent, Dropdown},
  setup() {
    const router = useRouter();
    let image = ref(null)
    const loggedUser = reactive({})
    return {
      userNavigation,
      router,
      image,
      loggedUser,
    }
  },
  methods:{
    logout: function(){
      this.$cookies.remove("token");
      this.router.push('/login')
    },
  },
  created() {
     this.loggedUser = this.$cookies.get('token').appUserDto
  },
  onMount(){
    this.image = localStorage.getItem(this.loggedUser.image)
  }
}
</script>
