'use client';                 // ← agora é o único componente client

import { AuthProvider } from '@/context/AuthContext';
import '../services/amplifyConfig';
import { ApolloProvider } from '@apollo/client';
import { apolloClient } from '@/services/apolloClient';

export default function Providers({ children }) {
  return <AuthProvider><ApolloProvider client={apolloClient}>{children}</ApolloProvider></AuthProvider>;
}
