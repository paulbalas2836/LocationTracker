<template>
  <div class="text-red-600 text-sm">
    * {{message}}
  </div>
</template>

<script>
export default {
  name: "ErrorMessage",
  props:{
    message:{
      default: "Input is wrong",
      type: String,
    },
  },
}
</script>