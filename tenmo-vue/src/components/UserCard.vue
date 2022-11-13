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
              <v-card width="400">
                <v-card-title class="text-center"
                  >Transaction options</v-card-title
                >
                <slot name="submitForm"></slot>
                <v-card-actions>
                  <v-row justify="space-around">
                    <v-btn color="primary" @click="dialog = false">Close</v-btn>
                    <slot name="sendTransfer"></slot>
                  </v-row>
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
  props: ["user"],

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