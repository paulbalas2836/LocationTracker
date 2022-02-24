<template>
  <label class="text-base font-medium text-gray-900">
    <span v-if="value">{{value}}</span>
    <span v-else><slot></slot></span>
  </label>
</template>

<script>
export default {
  name: "Label",
  props:['value'],
}
</script>
