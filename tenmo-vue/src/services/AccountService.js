import axios from 'axios'; 

export default {
      getAccountByUserId(userId) {
        return axios.get(`/account/userid/${userId}`);
      },
      
      listUsers() {
        return axios.get(`/user`);
      },
}