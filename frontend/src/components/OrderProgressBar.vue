<template>
  <div class="flex flex-col items-center w-full my-4">
    <div class="flex items-center w-3/4 mx-auto justify-between">
      <div v-for="(step, i) in steps" :key="i" class="flex-1 flex items-center">
        <div :class="[
          'w-10 h-10 flex items-center justify-center rounded-full font-bold text-lg transition',
          i <= activeIndex ? 'bg-teal-400 text-white' : 'bg-gray-200 text-gray-500'
        ]">
          {{ i + 1 }}
        </div>
        <div v-if="i < steps.length - 1" :class="[
          'flex-1 h-2',
          i < activeIndex ? 'bg-teal-400' : 'bg-gray-200'
        ]"></div>
      </div>
    </div>
    <div class="flex justify-between text-sm text-gray-600 w-3/4 mt-2 px-2">
      <span v-for="(step, i) in steps" :key="i" class="w-1/3 text-center">{{ step }}</span>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
const props = defineProps<{ status: string }>()
const steps = ['Commandée', 'Préparation', 'Terminée']
const activeIndex = computed(() => {
  switch (props.status) {
    case 'COMMANDEE': return 0
    case 'EN_PREPARATION': return 1
    case 'TERMINEE': return 2
    default: return 0
  }
})
</script>
