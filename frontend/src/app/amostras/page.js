// src/app/amostras/page.jsx
'use client';
import { useEffect, useState } from 'react';
import {
  getAmostras,
  getPropriedades,
  getTiposSolo,
} from '@/services/apiAmostra';        // ⬅️  adicione estes dois helpers
import Link from 'next/link';

export default function AmostrasPage() {
  const [rows, setRows]                     = useState([]);
  const [propRef, setPropRef]               = useState({});  // { id: nome }
  const [soloRef, setSoloRef]               = useState({});  // { id: nome }
  const [loading, setLoading]               = useState(true);
  const [error, setError]                   = useState(null);

  useEffect(() => {
    // carrega tudo em paralelo
    Promise.all([getAmostras(), getPropriedades(), getTiposSolo()])
      .then(([amostras, propriedades, tiposSolo]) => {
        // monta dicionários id → nome
        const propMap = propriedades.data.reduce(
          (map, p) => ({ ...map, [p.id]: p.nome }),
          {}
        );
        const soloMap = tiposSolo.data.reduce(
          (map, t) => ({ ...map, [t.id]: t.nome }),
          {}
        );

        setPropRef(propMap);
        setSoloRef(soloMap);
        setRows(amostras.data);
      })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p className="p-6">Carregando…</p>;
  if (error)   return <p className="p-6 text-red-500">{error}</p>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Amostras cadastradas</h1>

        <Link
          href="/amostras/novo"
          className="px-4 py-2 bg-green-600 hover:bg-green-700 rounded text-sm font-medium"
        >
          + Nova amostra
        </Link>

      <table className="min-w-full text-sm">
        <thead>
          <tr className="border-b border-gray-700">
            <th className="text-left py-2">ID</th>
            <th className="text-left py-2">Propriedade</th>
            <th className="text-left py-2">Tipo de Solo</th>
            <th className="text-left py-2 w-24"></th>
          </tr>
        </thead>
        <tbody>
          {rows.map(a => (
            <tr key={a.id} className="border-b border-gray-800">
              <td className="py-2">{a.id}</td>

              {/* usa lookup pelos IDs */}
              <td className="py-2">
                {propRef[a.propriedadeId] ?? '-'}
              </td>
              <td className="py-2">
                {soloRef[a.tipoSoloId] ?? '-'}
              </td>

              <td className="py-2">
                <Link
                  href={`/amostras/${a.id}`}
                  className="text-blue-400 hover:underline"
                >
                  Ver
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
