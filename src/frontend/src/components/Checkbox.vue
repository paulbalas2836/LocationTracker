<template>
  <input type="checkbox" :value="value" v-model="proxyChecked" class="rounded border-gray-300 text-blue-600 shadow-sm focus:border-indigo-300 focus:outline-none focus:rind-2 focus:ring-offset-2 focus:ring-blue-500">
</template>

<script>
export default {
  name: "Checkbox",
  emits: ['update:checked'],

  props:{
    checked: {
      type: [Array, Boolean],
      default: false
    },
    value: {
      default: null,
    },
  },
  computed:{
    proxyChecked:{
      get(){
        return this.checked
      },
      set(val) {
        this.$emit('update:checked', val)
      },
    },
  },
}
</script>
