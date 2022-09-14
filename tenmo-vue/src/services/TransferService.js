import axios from 'axios'; 

export default {

  listTranxByUserId(userId) {
    return axios.get(`/transfer/${userId}`);
  },

  getTransferById(transferId) {
    return axios.get(`/transfer/id/${transferId}`);
  },

  postTransfer(transfer) {
    return axios.post(`/transfer`, transfer);
  },

  test(transfer) {
    return axios.post(`/transfer/test`, transfer); 
  }
}