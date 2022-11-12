<template>
  <div id="transaction">
    <v-card>
      <v-card-title>Transaction Form</v-card-title>
      <v-label>FROM: {{ transferDTO.from }}</v-label>
      <v-label>TO: {{ transferDTO.to }}</v-label>
      <v-text-field></v-text-field>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "transaction-form",
  props: {
    transferDTO: {
      from: Number,
      to: Number,
      amount: Number,
    },
  },
  data() {
    return {};
  },
};
</script>

<style scoped>
</style>