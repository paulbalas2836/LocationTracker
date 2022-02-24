<template>
  <navigation-bar />
  <div class="flex flex-col">
  <change-image />
  <change-password />
  </div>
</template>

<script>
import NavigationBar from "@/components/NavigationBar";
import ChangeImage from "@/pages/profie/ChangeImage";
import ChangePassword from "@/pages/profie/ChangePassword";
export default {
  name: "Profile",
  components:{ChangePassword, ChangeImage, NavigationBar},

}
</script>
