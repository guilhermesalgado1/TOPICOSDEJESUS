import { ApolloClient, InMemoryCache, createHttpLink } from '@apollo/client';
import { setContext } from '@apollo/client/link/context';
import { Auth } from 'aws-amplify';

// Link HTTP para a URL da sua API GraphQL
const httpLink = createHttpLink({
  uri: 'http://localhost:4000/graphql'  // substitua pela URL real
});

// Link de contexto para inserir token JWT nos headers
const authLink = setContext(async (_, { headers }) => {
  try {
    const session = await Auth.currentSession();
    const token = session.getIdToken().getJwtToken();
    return {
      headers: {
        ...headers,
        Authorization: token ? `Bearer ${token}` : ""
      }
    };
  } catch {
    return { headers: { ...headers } };
  }
});

export const apolloClient = new ApolloClient({
  link: authLink.concat(httpLink),
  cache: new InMemoryCache()
});
