<template>
  <div id="user-card">
    <v-card class="mx-auto" max-width="344" variant="outlined">
      <v-card-item>
        <div class="text-center">
          <div class="text-overline mb-1">TEnmo User</div>
          <v-icon icon="mdi-account-circle" size="50px"></v-icon>
          <div class="text-h6 mb-1">{{ user.username }}</div>
          <div class="text-caption">{{ user.id }}</div>
        </div>
      </v-card-item>
      <v-divider></v-divider>
      <v-card-actions>
        <v-row justify="center">
          <v-btn color="primary" class="ma-2" @click="dialog = true"
            >Click User
            <v-dialog v-model="dialog">
              <v-card>
                <v-card-title class="text-center"
                  >Transaction options</v-card-title
                >
                <v-card-text>
                  <slot name="transferOp">
                    <v-btn color="secondary">Transfer</v-btn>
                  </slot>
                  <slot name="requestOp">
                    <v-btn color="secondary">Request</v-btn>
                  </slot>
                </v-card-text>
                <v-card-actions>
                  <v-btn color="primary" @click="dialog = false">Close</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-btn>
        </v-row>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "user-card",
  props: {
    user: {
      id: Number,
      username: String,
    },
  },

  data() {
    return {
      dialog: false,
    };
  },
};
</script>

<style >
#user-card {
  margin-top: 2%;
  margin-bottom: 2%;
}
</style>