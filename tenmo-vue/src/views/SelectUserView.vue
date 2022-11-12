<template>
  <div>
    <list-users />
  </div>
</template>

<script>
import ListUsers from "@/components/ListUsers.vue";
export default {
  name: "select-user-view",
  components: { ListUsers },
  data() {
    return {};
  },
};
</script>

<style scoped></style>
