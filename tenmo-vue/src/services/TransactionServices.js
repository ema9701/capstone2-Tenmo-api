import axios from 'axios';

export default {

    //TRANSFERS:

    listTransfersByUserId(userId) {
        return axios.get(`/transfer/${userId}`);
    },

    getTransferById(transferId) {
        return axios.get(`/transfer/id/${transferId}`);
    },

    postTransfer(transferDTO) {
        return axios.post(`/transfer`, transferDTO);
    },

    //REQUESTS:

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
        return axios.put(`/request/${requestId}?approve=${approve}`)
    }

}