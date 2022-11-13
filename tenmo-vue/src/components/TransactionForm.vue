<template>
  <div id="form-container">
    <v-form v-model="valid">
      <v-container>
        <v-row>
          <v-col cols="auto" md="12">
            <v-text-field
              v-model:transferDTO.from="from"
              type="number"
              label="From User Id"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="auto" md="12">
            <v-text-field
              v-model:transferDTO.to="to"
              type="number"
              label="To User Id"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="auto" md="12">
            <v-text-field
              type="number"
              v-model:transferDTO.amount="amount"
              label="Amount"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="auto" md="12">
            <v-radio-group inline required>
              <v-radio label="Transfer" value="true"></v-radio>
              <v-radio label="Request" value="false"></v-radio>
            </v-radio-group>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
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
    return {
      valid: false,
    };
  },
};
</script>

<style scoped>
</style>