<template>
  <div>
    <input class="block w-full bg-white border border-gray-300 rounded-md py-2 pl-3 pr-3 text-sm placeholder-gray-500 focus:outline-none focus:text-gray-900 focus:placeholder-gray-400 focus:ring-1 focus:ring-blue-500 sm:text-sm"
           :value="modelValue"
           @input="$emit('update:modelValue',$event.target.value)"
           ref="input"
           :type="type"  />
  </div>
</template>

<script>
export default {
  name: "Input",
  props: ['modelValue','type'],
  emits:['update:modelValue'],

  methods:{
    focus(){
      this.$refs.input.focus()
    },
  },
}
</script>
