const STORAGE_KEYS = {
      DOADORES: 'sd_doadores_v1',
      ESTOQUE: 'sd_estoque_v1',
      NOTIFS: 'sd_notifs_v1'
    };

    // níveis críticos
    const CRITICAL_THRESHOLD = 3;

    // utils
    function uid() { return Math.random().toString(36).slice(2, 9) }

    function save(key, data) { localStorage.setItem(key, JSON.stringify(data)) }
    function load(key, fallback) { const s = localStorage.getItem(key); return s ? JSON.parse(s) : fallback }

    // inicializa dados padrão se necessário
    function boot() {
      if (!localStorage.getItem(STORAGE_KEYS.ESTOQUE)) {
        const inicial = {
          "A+": 5, "A-": 2, "B+": 4, "B-": 1, "AB+": 3, "AB-": 1, "O+": 8, "O-": 2
        };
        save(STORAGE_KEYS.ESTOQUE, inicial);
      }
      if (!localStorage.getItem(STORAGE_KEYS.DOADORES)) {
        save(STORAGE_KEYS.DOADORES, []);
      }
      if (!localStorage.getItem(STORAGE_KEYS.NOTIFS)) {
        save(STORAGE_KEYS.NOTIFS, []);
      }
    }

    const EstoqueService = {
      listar() {
        return load(STORAGE_KEYS.ESTOQUE, {});
      },
      atualizar(tipo, qtd) {
        const e = EstoqueService.listar();
        e[tipo] = qtd;
        save(STORAGE_KEYS.ESTOQUE, e);
        // criar notificação automática se crítico
        if (qtd < CRITICAL_THRESHOLD) {
          NotificacaoService.criarParaTipoCritico(tipo);
        }
      },
      incrementar(tipo, delta) {
        const e = EstoqueService.listar();
        const val = (e[tipo] || 0) + delta;
        EstoqueService.atualizar(tipo, Math.max(0, val));
      },
      reset() {
        const empty = { "A+": 0, "A-": 0, "B+": 0, "B-": 0, "AB+": 0, "AB-": 0, "O+": 0, "O-": 0 };
        save(STORAGE_KEYS.ESTOQUE, empty);
      },
      tiposCriticos() {
        const e = EstoqueService.listar();
        return Object.entries(e).filter(([t, q]) => q < CRITICAL_THRESHOLD).map(([t, q]) => ({ tipo: t, qtd: q }));
      }
    };

    const DoadorService = {
      listar() { return load(STORAGE_KEYS.DOADORES, []); },
      salvar(d) {
        const all = DoadorService.listar();
        d.id = uid();
        all.push(d);
        save(STORAGE_KEYS.DOADORES, all);
        return d;
      },
      filtrarPorTipo(tipo) {
        const all = DoadorService.listar();
        if (!tipo) return all;
        return all.filter(x => x.tipo === tipo);
      },
      contar() { return DoadorService.listar().length; }
    };

    const NotificacaoService = {
      listar() { return load(STORAGE_KEYS.NOTIFS, []); },
      criar(msg) {
        const n = { id: uid(), mensagem: msg, createdAt: (new Date()).toISOString() };
        const all = NotificacaoService.listar();
        all.unshift(n);
        save(STORAGE_KEYS.NOTIFS, all);
        return n;
      },
      criarParaTipoCritico(tipo) {
        // notifica doadores disponíveis do tipo
        const disponiveis = DoadorService.filtrarPorTipo(tipo).filter(d => String(d.disponivel) === "true");
        const k = disponiveis.length;
        const texto = `Urgente: estoque de ${tipo} baixo (menos de ${CRITICAL_THRESHOLD}). ${k} doadores disponíveis notificados.`;
        NotificacaoService.criar(texto);
      },
      limpar() { save(STORAGE_KEYS.NOTIFS, []); }
    };

    const $ = sel => document.querySelector(sel);
    const $$ = sel => document.querySelectorAll(sel);

    function atualizarResumo() {
      $('#countDoadores').textContent = `Doadores: ${DoadorService.contar()}`;
      $('#resDoadores').textContent = DoadorService.contar();
      $('#resCriticos').textContent = EstoqueService.tiposCriticos().length;
      $('#resNotifs').textContent = NotificacaoService.listar().length;
    }

    function renderListaDoadores(tipoFiltro) {
      const list = DoadorService.filtrarPorTipo(tipoFiltro);
      const cont = $('#listaDoadores');
      cont.innerHTML = '';
      if (list.length === 0) { cont.innerHTML = '<div class="muted small">Nenhum doador encontrado.</div>'; return; }
      list.forEach(d => {
        const el = document.createElement('div');
        el.className = 'card';
        el.style.padding = '0.6rem';
        el.style.marginBottom = '0.5rem';
        el.innerHTML = `<div style="display:flex;align-items:center;gap:0.6rem">
          <div style="width:44px;height:44px;border-radius:8px;background:#fff3f3;color:var(--accent-dark);display:flex;align-items:center;justify-content:center;font-weight:800">${d.tipo}</div>
          <div style="flex:1">
            <div style="font-weight:700">${d.nome}</div>
            <div class="muted small">${d.idade} anos • ${d.peso} kg • ${d.contato || '—'}</div>
          </div>
          <div style="text-align:right">
            <div class="${d.disponivel === 'true' ? 'ok' : 'muted'}">${d.disponivel === 'true' ? 'Disponível' : 'Indisponível'}</div>
          </div>
        </div>`;
        cont.appendChild(el);
      });
    }

    function renderEstoque() {
      const tabela = $('#tabelaEstoque tbody');
      tabela.innerHTML = '';
      const estoque = EstoqueService.listar();
      const tiposOrdenados = Object.keys(estoque).sort();
      tiposOrdenados.forEach(tipo => {
        const qtd = estoque[tipo];
        const tr = document.createElement('tr');
        tr.className = qtd < CRITICAL_THRESHOLD ? 'critical' : '';
        tr.innerHTML = `<td style="font-weight:700">${tipo}</td>
          <td>${qtd}</td>
          <td>${qtd < CRITICAL_THRESHOLD ? `<span class="low">Crítico</span>` : `<span class="ok">OK</span>`}</td>
          <td>
            <button class="btn btn-ghost" data-action="add" data-tipo="${tipo}">+1</button>
            <button class="btn btn-ghost" data-action="sub" data-tipo="${tipo}">-1</button>
          </td>`;
        tabela.appendChild(tr);
      });

      tabela.querySelectorAll('button[data-action]').forEach(b => {
        b.addEventListener('click', (ev) => {
          const tipo = b.dataset.tipo;
          const action = b.dataset.action;
          if (action === 'add') EstoqueService.incrementar(tipo, 1);
          else EstoqueService.incrementar(tipo, -1);
          renderAll();
        });
      });
    }

    function renderNotificacoes() {
      const cont = $('#listaNotificacoes');
      cont.innerHTML = '';
      const n = NotificacaoService.listar();
      if (n.length === 0) { cont.innerHTML = '<div class="muted small">Sem notificações.</div>'; return; }
      n.forEach(item => {
        const el = document.createElement('div');
        el.className = 'notif';
        const d = new Date(item.createdAt);
        el.innerHTML = `<div style="font-weight:700">${item.mensagem}</div><div class="time">${d.toLocaleString()}</div>`;
        cont.appendChild(el);
      });
    }

    function renderAll() {
      atualizarResumo();
      renderListaDoadores($('#filtroTipo').value);
      renderEstoque();
      renderNotificacoes();
    }

    document.addEventListener('DOMContentLoaded', () => {
      boot();
      renderAll();

      $('#doadorForm').addEventListener('submit', (ev) => {
        ev.preventDefault();
        const nome = $('#nome').value.trim();
        const idade = parseInt($('#idade').value, 10);
        const peso = parseFloat($('#peso').value);
        const tipo = $('#tipo').value;
        const disponivel = $('#disponivel').value;
        const contato = $('#contato').value.trim();

        // validações simples conforme documentação: idade mínima 16
        if (!nome) { alert('Informe o nome do doador.'); return; }
        if (isNaN(idade) || idade < 16) { alert('Doador deve ter no mínimo 16 anos.'); return; }
        if (isNaN(peso) || peso < 40) { alert('Peso mínimo para doação é 40 kg (ajuste consoante regras locais).'); return; }
        if (!tipo) { alert('Selecione o tipo sanguíneo.'); return; }

        const novo = { nome, idade, peso, tipo, disponivel: String(disponivel), contato };
        DoadorService.salvar(novo);
        // após cadastrar, verificar estoques e possivelmente notificar (simulado)
        // ex: se existirem tipos críticos, enviar notificação de cadastro recebido
        const criticos = EstoqueService.tiposCriticos();
        if (criticos.length > 0) {
          NotificacaoService.criar(`Novo doador cadastrado (${nome}). Tipos críticos: ${criticos.map(c => c.tipo).join(', ')}.`);
        }
        // limpar form
        $('#doadorForm').reset();
        renderAll();
        alert('Doador cadastrado com sucesso!');
      });

      $('#limparForm').addEventListener('click', () => { $('#doadorForm').reset(); });

      $('#btnAtualizarEstoque').addEventListener('click', () => {
        const tipo = $('#tipoEstoque').value;
        const qtd = parseInt($('#qtdEstoque').value, 10);
        if (!tipo) { alert('Selecione um tipo sanguíneo para atualizar.'); return; }
        if (isNaN(qtd) || qtd < 0) { alert('Informe uma quantidade válida (>= 0).'); return; }
        EstoqueService.atualizar(tipo, qtd);
        $('#qtdEstoque').value = '';
        renderAll();
      });

      $('#btnResetEstoque').addEventListener('click', () => {
        if (!confirm('Tem certeza que deseja zerar todo o estoque?')) return;
        EstoqueService.reset();
        NotificacaoService.criar('Estoque zerado manualmente.');
        renderAll();
      });

      $('#filtroTipo').addEventListener('change', () => renderListaDoadores($('#filtroTipo').value));

      $('#limparNotificacoes').addEventListener('click', () => {
        if (!confirm('Limpar todas as notificações?')) return;
        NotificacaoService.limpar();
        renderAll();
      });

      if (DoadorService.contar() === 0) {
        DoadorService.salvar({ nome: 'Maria Oliveira', idade: 28, peso: 68, tipo: 'O+', disponivel: 'true', contato: '(51) 99999-0001' });
        DoadorService.salvar({ nome: 'Carlos Souza', idade: 34, peso: 80, tipo: 'A-', disponivel: 'false', contato: '(51) 99999-0002' });
        NotificacaoService.criar('Sistema iniciado com dados de exemplo.');
        renderAll();
      }

    }); 