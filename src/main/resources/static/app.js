const E = {
  pacientes: {t:'Pacientes', f:[['nome','Nome'],['cpf','CPF'],['dataNascimento','Nascimento','date'],['telefone','Telefone'],['email','E-mail'],['login','Login vinculado']]},
  medicos: {t:'Médicos', f:[['nome','Nome'],['crm','CRM'],['especialidade','Especialidade'],['telefone','Telefone'],['login','Login vinculado']]},
  funcionarios: {t:'Funcionários', f:[['nome','Nome'],['cpf','CPF'],['cargo','Cargo'],['telefone','Telefone'],['login','Login vinculado']]},
  consultas: {t:'Consultas', f:[['paciente','Paciente','ref:pacientes'],['medico','Médico','ref:medicos'],['dataHora','Data/Hora','datetime-local'],['motivo','Motivo'],['status','Status','select:AGENDADA,REALIZADA,CANCELADA']]},
  prontuarios: {t:'Prontuários', f:[['consulta','Consulta','ref:consultas'],['descricao','Descrição','textarea'],['diagnostico','Diagnóstico'],['prescricao','Prescrição']]},
  usuarios: {t:'Usuários', f:[['login','Login'],['senha','Senha (vazio = manter)','password'],['perfil','Perfil','select:ADMIN,MEDICO,FUNCIONARIO,PACIENTE'],['ativo','Ativo','checkbox']]}
};
const ACESSO = {ADMIN:['pacientes','medicos','funcionarios','consultas','prontuarios','usuarios'], FUNCIONARIO:['pacientes','consultas'], MEDICO:['pacientes','consultas','prontuarios'], PACIENTE:['consultas']};
let me, cur, rows = [];
const $ = id => document.getElementById(id);
const lab = o => o.nome || (o.paciente ? `#${o.id} ${o.paciente.nome} ${(o.dataHora||'').replace('T',' ')}` : o.login || o.id);
const fmt = v => v == null ? '' : typeof v == 'object' ? lab(v) : String(v).replace('T',' ');
const ro = () => me.perfil == 'PACIENTE' || (me.perfil == 'MEDICO' && cur == 'pacientes');

async function api(u, o = {}) {
  const r = await fetch(u, {headers:{'Content-Type':'application/json'}, ...o});
  if (r.status == 401) { location = '/login.html'; return; }
  if (!r.ok) throw new Error(await r.text() || r.status);
  return r.json().catch(() => null);
}
async function init() {
  me = await api('/api/me');
  $('user').textContent = `${me.login} (${me.perfil})`;
  $('menu').innerHTML = ACESSO[me.perfil].map(k => `<a href="#" onclick="abrir('${k}')">${E[k].t}</a>`).join('');
  abrir(ACESSO[me.perfil][0]);
}
async function abrir(k) { cur = k; rows = await api('/api/' + k); desenhar(); }
function desenhar() {
  const cols = E[cur].f.filter(f => f[2] != 'password');
  $('titulo').textContent = E[cur].t; $('form').style.display = 'none'; $('btnNovo').style.display = ro() ? 'none' : '';
  $('tabela').innerHTML = '<tr>' + cols.map(f => `<th>${f[1]}</th>`).join('') + '<th></th></tr>' +
    rows.map(r => '<tr>' + cols.map(f => `<td>${fmt(r[f[0]])}</td>`).join('') +
      `<td>${ro() ? '' : `<button onclick="editar(${r.id})">Editar</button> <button onclick="excluir(${r.id})">Excluir</button>`}</td></tr>`).join('');
}
const novo = () => form({});
const editar = id => form(rows.find(r => r.id == id));
async function form(r) {
  let h = '';
  for (const [k, l, t = 'text'] of E[cur].f) {
    let v = r[k], i;
    if (t.startsWith('ref:')) { const o = await api('/api/' + t.slice(4));
      i = `<select id="f_${k}">${o.map(x => `<option value="${x.id}" ${v && v.id == x.id ? 'selected' : ''}>${lab(x)}</option>`).join('')}</select>`; }
    else if (t.startsWith('select:')) i = `<select id="f_${k}">${t.slice(7).split(',').map(x => `<option ${v == x ? 'selected' : ''}>${x}</option>`).join('')}</select>`;
    else if (t == 'textarea') i = `<textarea id="f_${k}">${v || ''}</textarea>`;
    else if (t == 'checkbox') i = `<input type="checkbox" id="f_${k}" style="width:auto" ${v !== false ? 'checked' : ''}>`;
    else i = `<input type="${t}" id="f_${k}" value="${t == 'datetime-local' && v ? v.slice(0,16) : (v ?? '')}">`;
    h += `<label>${l}${i}</label>`;
  }
  $('campos').innerHTML = h; $('f_id').value = r.id || ''; $('form').style.display = 'block';
}
async function salvar(e) {
  e.preventDefault(); const b = {};
  for (const [k, , t = 'text'] of E[cur].f) { const el = $('f_' + k);
    b[k] = t.startsWith('ref:') ? {id: +el.value} : t == 'checkbox' ? el.checked : el.value || null; }
  const id = $('f_id').value;
  try { await api('/api/' + cur + (id ? '/' + id : ''), {method: id ? 'PUT' : 'POST', body: JSON.stringify(b)}); abrir(cur); }
  catch (x) { alert('Erro: ' + x.message); }
}
async function excluir(id) {
  if (!confirm('Excluir registro?')) return;
  try { await api('/api/' + cur + '/' + id, {method:'DELETE'}); abrir(cur); } catch (x) { alert('Erro: ' + x.message); }
}
init();
