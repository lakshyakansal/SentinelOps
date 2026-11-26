import re

def words(text: str) -> set[str]:
    return set(re.findall(r"[a-z0-9_]{3,}", (text or "").lower()))

async def retrieve_similar(service_name: str, logs: str) -> list[dict]:
    # Small deterministic local corpus keeps the demo useful without downloading a model.
    corpus = [
        {"title":"Order API connection pool saturation",
         "root_cause":"Connection pool exhausted during traffic spike",
         "resolution":"Investigate connection leaks and transaction lifetime.",
         "log_text":"hikaripool connection not available timeout database connections"},
        {"title":"Checkout service memory pressure",
         "root_cause":"Heap pressure after cache retention regression",
         "resolution":"Review heap dump and cache eviction policy.",
         "log_text":"outofmemoryerror java heap space memory limit"}
    ]
    q = words(logs)
    ranked = []
    for item in corpus:
        d = words(item["log_text"])
        score = len(q & d) / max(1, len(q | d))
        if score > 0:
            ranked.append({"id":None,"title":item["title"],"root_cause":item["root_cause"],
                           "resolution":item["resolution"],"similarity":round(score,3)})
    # pgvector query is an explicit next step documented in docs/RAG.md.
    return sorted(ranked,key=lambda x:x["similarity"],reverse=True)[:3]
