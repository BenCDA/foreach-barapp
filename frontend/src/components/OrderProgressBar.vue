<template>
  <div class="w-full">
    <div class="flex items-center justify-between mb-2">
      <div v-for="(step, i) in steps" :key="i" class="flex-1 flex items-center">
        <div
          :class="[
            'w-8 h-8 flex items-center justify-center rounded-full font-bold',
            i <= activeIndex ? 'bg-teal-400 text-white' : 'bg-gray-200 text-gray-500'
          ]"
        >
          {{ i + 1 }}
        </div>
        <div v-if="i < steps.length - 1"
          :class="[
            'flex-1 h-1',
            i < activeIndex ? 'bg-teal-400' : 'bg-gray-200'
          ]"
        ></div>
      </div>
    </div>
    <div class="flex justify-between text-xs text-gray-600 px-1">
      <span v-for="(label, i) in steps" :key="i">{{ label }}</span>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed } from 'vue'
const props = defineProps<{ status: string }>()

const steps = ['Commandée', 'En préparation', 'Terminée']

const activeIndex = computed(() => {
  switch (props.status) {
    case 'COMMANDEE':       return 0
    case 'EN_PREPARATION':  return 1
    case 'TERMINEE':        return 2
    default:                return 0
  }
})
</script>
