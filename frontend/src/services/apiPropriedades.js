import api from './api';           // a mesma instância Axios já configurada

// GET /propriedades         → lista todas
export const getPropriedades = () => api.get('/propriedades');

// GET /propriedades/{id}    → buscar 1
export const getPropriedade  = id  => api.get(`/propriedades/${id}`);

// PUT /propriedades/{id}    → atualizar
export const updatePropriedade = (id, body) =>
  api.put(`/propriedades/${id}`, body);

export const createPropriedade = body          => api.post('/propriedades', body);