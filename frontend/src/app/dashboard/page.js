'use client';
import { useEffect, useState } from 'react';
import { useContext } from 'react';
import { AuthContext } from '@/context/AuthContext';
import { redirect } from 'next/navigation';
import Card from '@/app/components/Card'; // ajuste o path se necessário
import PessoasButton from './PessoasButton';
import TipoSoloButton from './TipoSoloButton';

import {
  getLastAmostra,
  getLastPropriedade,
  getLastTipoSolo,
} from '@/services/apiHelpers';

export default function Dashboard() {
  const [data, setData] = useState({
    amostra:      null,
    propriedade:  null,
    tipoSolo:     null,
  });

  const [loading, setLd] = useState(true);

  useEffect(() => {
    Promise.all([getLastAmostra(), getLastPropriedade(), getLastTipoSolo()])
      .then(([amostra, propriedade, tipoSolo]) =>
        setData({ amostra, propriedade, tipoSolo })
      )
      .finally(() => setLd(false));
  }, []);

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

      <div className="p-6 grid md:grid-cols-3 gap-4">
        {/* --- CARD AMOSTRA --- */}
        <Card
          title="Última Amostra"
          item={data.amostra}
          entity="amostras"
          fields={['id']}
        />

        {/* --- CARD PROPRIEDADE --- */}
        <Card
          title="Última Propriedade"
          item={data.propriedade}
          entity="propriedades"
          fields={['nome', 'localizacao']}
        />

        {/* --- CARD TIPO SOLO --- */}
        <Card
          title="Último Tipo de Solo"
          item={data.tipoSolo}
          entity="tiposSolo"
          fields={['nome', 'descricao']}
        />
      </div>
    </div>
  );
}
