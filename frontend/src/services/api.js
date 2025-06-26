// src/services/api.js
import axios from 'axios';
import { Auth } from 'aws-amplify';

const api = axios.create({
  baseURL: 'http://localhost:8080', // se seu back expõe /api
});

// adiciona JWT antes de cada requisição
api.interceptors.request.use(async (config) => {
  try {
    const session = await Auth.currentSession();
    const token   = session.getAccessToken().getJwtToken();
    config.headers.Authorization = `Bearer ${token}`;
    return config;
  } catch {
    // não há usuário → opcional: redirecionar ao login
    return config;
  }
});
export default api;
