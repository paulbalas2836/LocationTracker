<template>
  <transition enter-active-class="transition ease-out duration-200" enter-from-class="transform opacity-0 scale-95" enter-to-class="transform opacity-100 scale-100" leave-active-class="transition ease-in duration-75" leave-from-class="transform opacity-100 scale-100" leave-to-class="transform opacity-0 scale-95">
<div v-if="active" class="right-0 mt-2 w-48 rounded-md shadow-lg py-1 bg-white ring-1 ring-black ring-opacity-5 focus:outline-none">
  <slot />
</div>
  </transition>
</template>

<script>
export default {
  name: "DropdownContent",
  inject:['sharedState'],
  computed: {
    active() {
      return this.sharedState.active;
    }
  }
}
</script>

<style scoped>

</style>