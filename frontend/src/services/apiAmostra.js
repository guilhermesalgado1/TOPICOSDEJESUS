import api from './api';          // sua instância axios com JWT

export const getAmostras = () => api.get('/grade-amostral');
export const getAmostra  = id => api.get(`/grade-amostral/${id}`);  // GET detalhe
export const getPropriedades  = () => api.get('/propriedades');      // GET /propriedade
export const getTiposSolo     = () => api.get('/tipos-solo');       // GET /tipos-solo
export const updateAmostra  = (id, payload) => api.put(`/grade-amostral/${id}`, payload);
export const createAmostra = (body) => api.post('/grade-amostral', body);

