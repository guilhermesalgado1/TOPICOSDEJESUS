'use client';
import { useEffect, useState } from 'react';
import { listarPessoas } from '@/services/apiPessoas';
import Link from 'next/link';

export default function PessoasPage() {
  const [rows, setRows]   = useState([]);
  const [loading, setL]   = useState(true);
  const [error, setErr]   = useState(null);

  useEffect(() => {
    listarPessoas()
      .then(r => setRows(r.data?.listarPessoas ?? []))
      .catch(err => setErr(err.message))
      .finally(() => setL(false));
  }, []);

  if (loading) return <p className="p-6">Carregando…</p>;
  if (error)   return <p className="p-6 text-red-500">{error}</p>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Pessoas cadastradas</h1>

      <Link href="/pessoas/novo"
            className="mb-4 inline-block rounded bg-blue-600 px-3 py-1 text-sm hover:bg-blue-700">
        + Nova Pessoa
      </Link>

      <table className="min-w-full text-sm">
        <thead>
          <tr className="border-b border-gray-700">
            <th className="text-left py-2">ID</th>
            <th className="text-left py-2">Nome</th>
            <th className="text-left py-2">E-mail</th>
            <th className="w-24"></th>
          </tr>
        </thead>
        <tbody>
          {rows.map(p => (
            <tr key={p.id} className="border-b border-gray-800">
              <td className="py-2">{p.id}</td>
              <td className="py-2">{p.nome}</td>
              <td className="py-2">{p.email}</td>
              <td className="py-2">
                <Link href={`/pessoas/${p.id}`} className="text-blue-400 hover:underline">
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
