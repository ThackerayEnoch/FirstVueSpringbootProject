<template>
    <router-view/>
</template>

<script>
import {mapMutations} from 'vuex'
import '@/assets/css/global.css'


export default {
  name: 'App',
  methods: {
    ...mapMutations(['addCount']),
  },
}
</script>

<style scoped>

</style>
