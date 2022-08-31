import axios from 'axios'; 

export default {
  
  listRequestsByUserId(userId) {
    return axios.get(`/request/list/${userId}`);
  },

  getRequestById(requestId) {
    return axios.get(`/request/${requestId}`);
  },

  postRequest(request) {
    return axios.post(`/request`, request); 
  }, 

  approveOrDenyRequest(request, requestId) {
    return axios.put(`/request/${requestId}`, request); 
  }

}