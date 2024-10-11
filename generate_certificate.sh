#!/bin/bash 

vaalidate_input(){
    if [ -z "$1" ]; then 
        echo "Erro: Campo nao pode ser vazzio."
        return 1
    fi 
    return 0
}

get_input (){
        local prompt="$1"
    local input
    while true; do
        read -p "$prompt: " input
        if validate_input "$input"; then
            echo "$input"
            return 0
        fi
    done
}

domain=$(get_input "Digite o nome do domínio ou IP para o certificado")
organization=$(get_input "Digite o nome da organização")
city=$(get_input "Digite a cidade")
state=$(get_input "Digite o estado")
country=$(get_input "Digite o código do país (2 letras)")

openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout server.key -out server.crt \
    -subj "/CN=$domain/O=$organization/L=$city/ST=$state/C=$country"

chmod 600 server.key
chmod 644 server.crt

echo "Certificado gerado com sucesso!"