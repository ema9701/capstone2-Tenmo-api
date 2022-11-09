import axios from 'axios'; 

export default {
  
  listRequestsByUserId(userId) {
    return axios.get(`/request/list/${userId}`);
  },

  getRequestById(requestId) {
    return axios.get(`/request/${requestId}`);
  },

  postRequest(requestDTO) {
    return axios.post(`/request`, requestDTO); 
  }, 

  updateStatus(requestId, approve) {
    return axios.put(`/request/${requestId}`, approve)
  }

}