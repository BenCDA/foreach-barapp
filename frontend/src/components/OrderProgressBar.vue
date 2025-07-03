<template>
  <div class="w-full">
    <div class="relative w-full h-8 flex items-center mb-2">
      <div class="absolute left-0 right-0 h-1 bg-gray-200" style="top: 14px;"></div>

      <div
        class="absolute left-0 h-1 bg-teal-400 transition-all duration-500 ease-in-out"
        :style="{ width: `${(activeIndex / (steps.length - 1)) * 100}%`, top: '14px' }"
      ></div>

      <div
        v-for="(step, i) in steps"
        :key="i"
        class="absolute w-8 h-8 flex items-center justify-center rounded-full font-bold z-10"
        :class="[
          i <= activeIndex ? 'bg-teal-400 text-white' : 'bg-gray-200 text-gray-500'
        ]"
        :style="{
          left: `${(i / (steps.length - 1)) * 100}%`,
          // Positionne le haut du cercle à 14px du haut du parent
          // Puis décale le cercle de la moitié de sa propre hauteur (16px) vers le haut (pour centrer)
          // Donc 14px - 16px = -2px. C'est pourquoi on ajoute 'calc(14px - 16px)' ou directement '-2px'
          // Ou plus simple: top: 14px (pour aligner le haut du cercle avec le top de la ligne)
          // Puis on le translate verticalement de -50% de sa propre hauteur pour que son centre soit à 14px
          top: '14px', // Le haut du cercle sera à 14px
          transform: 'translateX(-50%) translateY(-50%)' // Centre le cercle sur ce point
        }"
      >
        {{ i + 1 }}
      </div>
    </div>

    <div class="flex justify-between text-xs text-gray-600 mt-1">
      <span
        v-for="(label, i) in steps"
        :key="i"
        class="flex-1 text-center"
      >{{ label }}</span>
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

<style scoped>
/* Pas besoin de CSS additionnel si Tailwind est configuré. */
</style>