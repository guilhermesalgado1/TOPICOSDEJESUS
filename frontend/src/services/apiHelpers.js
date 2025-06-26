// src/services/apiHelpers.js
import { getAmostras }      from './apiAmostra';
import { getPropriedades }  from './apiPropriedades';
import { getTiposSolo }     from './apiTiposSolo';

// se o back aceitar ?sort=id,desc&size=1, use isso.
// aqui faço no front pra não depender:
export async function getLastAmostra() {
  const r = await getAmostras();
  return r.data.sort((a, b) => b.id - a.id)[0] ?? null;
}

export async function getLastPropriedade() {
  const r = await getPropriedades();
  return r.data.sort((a, b) => b.id - a.id)[0] ?? null;
}

export async function getLastTipoSolo() {
  const r = await getTiposSolo();
  return r.data.sort((a, b) => b.id - a.id)[0] ?? null;
}
