import type {Incident} from './types'
const base=import.meta.env.VITE_API_BASE_URL||'http://localhost:8080'
async function req<T>(path:string,init?:RequestInit):Promise<T>{const r=await fetch(base+path,{...init,headers:{'Content-Type':'application/json',...(init?.headers||{})}});if(!r.ok)throw new Error(`API ${r.status}: ${await r.text()}`);return r.json()}
export const api={incidents:()=>req<Incident[]>('/api/v1/incidents'),alert:(body:unknown)=>req('/api/v1/alerts',{method:'POST',body:JSON.stringify(body)}),status:(id:number,status:string)=>req<Incident>(`/api/v1/incidents/${id}/status`,{method:'PATCH',body:JSON.stringify({status})}),investigate:(id:number)=>req<Incident>(`/api/v1/incidents/${id}/investigate`,{method:'POST'})}
