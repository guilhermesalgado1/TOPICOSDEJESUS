'use client';
import { useContext } from 'react';
import { AuthContext } from '@/context/AuthContext';
import { redirect } from 'next/navigation';
import PessoasButton from './PessoasButton';
import TipoSoloButton from './TipoSoloButton';

export default function Dashboard() {

  const { user, logout } = useContext(AuthContext);

  // Fallbacks seguros
  const email = user?.username                          // User Pool alias

  if (!user) {
    // server-side redirect (folded no hydration) – opcional
    redirect('/');
  }







  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">
         Bem-vindo{email ? `, ${email}` : ''}!
      </h1>

      <p>Você está logado e pode acessar os dados protegidos.</p>

      <button onClick={logout} className="mt-4 text-blue-500 underline">
        Sair
      </button>

      <TipoSoloButton />

      <PessoasButton />
    </div>
  );
}
