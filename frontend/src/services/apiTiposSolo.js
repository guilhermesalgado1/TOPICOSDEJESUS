import api from './api';          // a instância Axios já configurada c/ token

export const getTiposSolo   = ()         => api.get('/tipos-solo');
export const getTipoSolo    = id         => api.get(`/tipos-solo/${id}`);
export const createTipoSolo = body       => api.post('/tipos-solo', body);
export const updateTipoSolo = (id, body) => api.put(`/tipos-solo/${id}`, body);