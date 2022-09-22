import axios from 'axios'; 

export default {

  listTransfersByUserId(userId) {
    return axios.get(`/transfer/${userId}`);
  },

  getTransferById(transferId) {
    return axios.get(`/transfer/id/${transferId}`);
  },

  postTransfer(transfer) {
    return axios.post(`/transfer`, transfer);
  },


}