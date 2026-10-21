// Seu Nutri — Script simples (login e home)
// Somente JavaScript puro. Sem bibliotecas.
// Melhorias de uso: mostrar/ocultar senha, menu mobile e confirmação de exclusão.

document.addEventListener('DOMContentLoaded', function () {

    // 1) Mostrar/ocultar senha no login
    var btnSenha = document.getElementById('btn-mostrar-senha');
    var inputSenha = document.getElementById('senha');

    if (btnSenha && inputSenha) {
        btnSenha.addEventListener('click', function () {
            var tipo = inputSenha.getAttribute('type');
            if (tipo === 'password') {
                inputSenha.setAttribute('type', 'text');
                btnSenha.textContent = '🙈';
                btnSenha.setAttribute('aria-label', 'Ocultar senha');
            } else {
                inputSenha.setAttribute('type', 'password');
                btnSenha.textContent = '👁️';
                btnSenha.setAttribute('aria-label', 'Mostrar senha');
            }
        });
    }

    // 2) Abrir/fechar menu no celular (home)
    var btnMenu = document.getElementById('btn-menu-mobile');
    var listaLinks = document.getElementById('lista-links');

    if (btnMenu && listaLinks) {
        btnMenu.addEventListener('click', function () {
            if (listaLinks.classList.contains('aberto')) {
                listaLinks.classList.remove('aberto');
            } else {
                listaLinks.classList.add('aberto');
            }
        });
    }

    // 3) Confirmação antes de excluir (Prato, Ranque)
    var formsExcluir = document.querySelectorAll('form[data-confirmar]');
    formsExcluir.forEach(function (form) {
        form.addEventListener('submit', function (event) {
            var mensagem = form.getAttribute('data-confirmar');
            if (!confirm(mensagem)) {
                event.preventDefault();
            }
        });
    });

});
