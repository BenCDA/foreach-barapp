<!-- src/components/CategoryCard.vue -->
<template>
  <div
    class="cursor-pointer bg-white rounded-lg shadow p-4 hover:shadow-lg transition text-center"
    @click="$emit('click')"
  >
    <h3 class="text-lg font-semibold text-gray-800">{{ category.name }}</h3>
  </div>
</template>

<script lang="ts" setup>
import { defineProps } from 'vue'
import type { Category } from '../types'

const props = defineProps<{
  category: Category
}>()
</script>
