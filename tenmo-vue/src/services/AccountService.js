import axios from 'axios'; 

export default {
      getAccountByUserId(userId) {
        return axios.get(`/account/userid/${userId}`);
      },

      getAccountByAccountId(accountId) {
        return axios.get(`/account/${accountId}`);
      },

      getAcctIdByUsername(username) {
        return axios.get(`/account/idbyname/${username}`);
      },

      getAcctIdByUserId(userId) {
        return axios.get(`/account/accountid/${userId}`);
      },

      getUserIdByUsername(username) {
        return axios.get(`/user/${username}`);
      }, 
      
      listUsers() {
        return axios.get(`/user`);
      },
}