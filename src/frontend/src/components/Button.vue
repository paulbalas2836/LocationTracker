<template>
  <button
      :class="`items-center text-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white ${color} ${hover} focus:outline-none focus:ring-2 ${ring} focus:ring-offset-2 sm:px-10`">
    <slot></slot>
  </button>
</template>

<script>
export default {
  name: "Button",
  props:{
    color:{
      type:String,
      default: "bg-blue-500"
    },
    hover:{
      type: String,
      default: "hover:bg-blue-600"
    },
    ring:{
      type:String,
      default: "focus:ring-blue-500"
    }
  }
}
</script>
