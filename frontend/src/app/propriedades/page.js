'use client';
import { useEffect, useState } from 'react';
import Link from 'next/link';
import { getPropriedades } from '@/services/apiPropriedades';


export default function PropriedadesPage() {
  const [rows, setRows]     = useState([]);
  const [loading, setLoad ] = useState(true);
  const [error , setError ] = useState(null);

  useEffect(() => {
    getPropriedades()
      .then(r => setRows(r.data))
      .catch(err => setError(err.message))
      .finally(() => setLoad(false));
  }, []);

  if (loading) return <p className="p-6">Carregando…</p>;
  if (error)   return <p className="p-6 text-red-500">{error}</p>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Propriedades cadastradas</h1>


        <Link
        href="/propriedades/novo"
        className="inline-block mb-4 px-4 py-2 bg-blue-600 hover:bg-blue-700 rounded"
        >
        + Nova propriedade
        </Link>

      <table className="min-w-full text-sm">
        <thead>
          <tr className="border-b border-gray-700">
            <th className="text-left py-2">ID</th>
            <th className="text-left py-2">Nome</th>
            <th className="text-left py-2">Localização</th>
            <th className="w-24"></th>
          </tr>
        </thead>
        <tbody>
          {rows.map(p => (
            <tr key={p.id} className="border-b border-gray-800">
              <td className="py-2">{p.id}</td>
              <td className="py-2">{p.nome}</td>
              <td className="py-2">{p.localizacao ?? '-'}</td>
              <td className="py-2">
                <Link
                  href={`/propriedades/${p.id}`}
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
