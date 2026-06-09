#!/bin/bash
# Variáveis
grupoRecursos=rg-azuredevops-docker
regiao=brazilsouth
rm=rm560698                              # <-- coloque o SEU RM

sqlServer="sqlserver-terraorbit-$rm"     # nome único global, tudo minúsculo
sqlDb="terraorbitdb"
sqlAdmin="sqladmin"
sqlPassword="$SQL_PASSWORD"              # vem da variável de ambiente

# 1) Cria o servidor SQL lógico
az sql server create \
  --name $sqlServer \
  --resource-group $grupoRecursos \
  --location $regiao \
  --admin-user $sqlAdmin \
  --admin-password "$sqlPassword"

# 2) Cria o banco (camada Basic, a mais barata)
az sql db create \
  --resource-group $grupoRecursos \
  --server $sqlServer \
  --name $sqlDb \
  --service-objective Basic

# 3) Libera acesso dos serviços do Azure (seu container/web app)
az sql server firewall-rule create \
  --resource-group $grupoRecursos --server $sqlServer \
  --name AllowAzureServices \
  --start-ip-address 0.0.0.0 --end-ip-address 0.0.0.0

# 4) Libera acesso externo (para o Query Editor do portal e clientes)
#    ATENÇÃO: regra permissiva, só para ambiente de trabalho/teste
az sql server firewall-rule create \
  --resource-group $grupoRecursos --server $sqlServer \
  --name AllowAll \
  --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255

echo "Servidor:  $sqlServer.database.windows.net"
echo "Banco:     $sqlDb"
echo "Usuário:   $sqlAdmin"
