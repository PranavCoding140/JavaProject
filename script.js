(function(){
  "use strict";
  var $ = function(s){return document.querySelector(s)};
  var LEVELS = [
    {n:"Stay on the topic", d:"Every question is about the topic and what you described. Nothing else.",
     rule:"Every question must be squarely about the topic and the specifics in the description. No tangents at all."},
    {n:"Mostly on topic", d:"Core questions, with one or two from directly neighbouring ideas.",
     rule:"About 8 questions squarely on the topic; 2 may touch an idea directly adjacent to it."},
    {n:"Around the topic", d:"A mix of core material, related concepts and real uses.",
     rule:"About half on the core topic; the rest on related concepts, prerequisites, applications and common comparisons."},
    {n:"The wider field", d:"Core, plus history, people, applications and sibling topics.",
     rule:"Only 2 or 3 on the core topic. The rest come from the wider subject: history, key people, real-world applications, sibling topics, and how the topic connects to other areas."},
    {n:"Anywhere nearby", d:"Questions can come from anywhere around the subject, even far from the topic.",
     rule:"At most 1 question on the core topic. The rest roam freely across the whole surrounding subject area and its neighbouring fields: lateral, surprising, and varied in type and subject, yet still defensible as part of the same broad subject."}
  ];
  var DEPTH = [
    {name:"Introductory", hint:"No description yet. Basic, widely known ideas.",
     rule:"Introductory: recall of basic definitions and widely known facts. Short, direct questions."},
    {name:"Foundational", hint:"Add detail to make it harder.",
     rule:"Foundational: core definitions plus simple 'why' and 'which' questions. A learner who has read one chapter should manage most."},
    {name:"Intermediate", hint:"Specific enough for application questions.",
     rule:"Intermediate: mix recall with application, short worked reasoning, comparisons and identifying errors. Use the specific terms from the description."},
    {name:"In-depth", hint:"Detailed. Expect multi-step reasoning.",
     rule:"In-depth: mostly application, analysis and edge cases. Include multi-step reasoning, subtle distinctions, trade-offs, and short calculations or scenarios where the topic allows. Distractors must be tempting to someone who half-knows the material."}
  ];
  var LETTERS = ["A","B","C","D"];

  var S = {topic:"", desc:"", level:3, qs:[], i:0, picks:[], asked:[], ctl:null};
  var sample = null;

  /* ---------- helpers ---------- */
  function show(id){
    ["setup","loading","quiz","result"].forEach(function(s){ $("#"+s).classList.toggle("hidden", s!==id); });
    window.scrollTo(0,0);
  }
  function wc(t){ t=t.trim(); return t ? t.split(/\s+/).length : 0; }
  function depthOf(desc){ var w=wc(desc); return w===0?0 : w<12?1 : w<40?2 : 3; }
  function shuffle(a){ for(var i=a.length-1;i>0;i--){var j=Math.floor(Math.random()*(i+1));var t=a[i];a[i]=a[j];a[j]=t;} return a; }
  function el(tag, cls, txt){ var e=document.createElement(tag); if(cls) e.className=cls; if(txt!=null) e.textContent=txt; return e; }
  function save(){ try{ localStorage.setItem("tq-setup", JSON.stringify({t:S.topic,d:S.desc,l:S.level})); }catch(e){} }
  function restore(){
    try{
      var v = JSON.parse(localStorage.getItem("tq-setup")||"null");
      if(v){ $("#topic").value=v.t||""; $("#desc").value=v.d||""; if(v.l>=1&&v.l<=5) S.level=v.l; }
    }catch(e){}
  }

  /* ---------- depth meter ---------- */
  function updateDepth(){
    var d = depthOf($("#desc").value);
    var segs = $("#meter").children;
    for(var i=0;i<4;i++) segs[i].classList.toggle("on", i<=d);
    $("#depthname").textContent = DEPTH[d].name;
    $("#depthhint").textContent = DEPTH[d].hint;
  }

  /* ---------- level dial ---------- */
  var RING = [40,54,68,82,96];
  var GOLDEN = 2.399963;
  var DOTS = [];
  function buildDial(){
    var g = $("#pts");
    for(var i=0;i<10;i++){
      var c = document.createElementNS("http://www.w3.org/2000/svg","circle");
      c.setAttribute("class","pt"); c.setAttribute("cx","100"); c.setAttribute("cy","100"); c.setAttribute("r","4.5");
      g.appendChild(c); DOTS.push(c);
    }
    var box = $("#levels");
    for(var n=1;n<=5;n++){
      (function(n){
        var b = el("button",null,String(n)); b.type="button";
        b.setAttribute("aria-label","Level "+n+": "+LEVELS[n-1].n);
        b.addEventListener("click", function(){ S.level=n; renderDial(); });
        box.appendChild(b);
      })(n);
    }
    renderDial();
  }
  function renderDial(){
    var R = RING[S.level-1];
    $("#zone").style.transform = "scale("+(R/96)+")";
    DOTS.forEach(function(c,i){
      var f = Math.sqrt((i+0.6)/10.4);
      var rr = Math.max(14, R*f*0.92);
      var a = i*GOLDEN;
      c.style.transform = "translate("+(Math.cos(a)*rr).toFixed(1)+"px,"+(Math.sin(a)*rr).toFixed(1)+"px)";
    });
    Array.prototype.forEach.call($("#levels").children, function(b,i){ b.setAttribute("aria-pressed", String(i+1===S.level)); });
    $("#lvname").textContent = "Level "+S.level+": "+LEVELS[S.level-1].n;
    $("#lvdesc").textContent = LEVELS[S.level-1].d;
  }

  /* ---------- prompt ---------- */
  function buildPrompt(){
    var d = depthOf(S.desc);
    var prev = S.asked.slice(-40);
    var p = [];
    p.push("You are an expert exam setter. Write a fresh multiple-choice quiz.");
    p.push("Return ONLY a JSON array. No prose, no markdown, no code fences.");
    p.push("");
    p.push("TOPIC: "+S.topic);
    p.push("LEARNER'S DESCRIPTION: "+(S.desc.trim()||"(none given)"));
    p.push("");
    p.push("DEPTH. "+DEPTH[d].rule+" Judge depth by how specific and technical the description is, not only by its length. A short description naming an advanced idea is still advanced.");
    p.push("DIVERSITY LEVEL "+S.level+" of 5. "+LEVELS[S.level-1].rule);
    p.push("");
    p.push("Write exactly 10 questions. Each has exactly 4 options and exactly one correct option.");
    p.push('Each array item: {"q": string, "options": [4 strings], "answer": index 0-3, "explanation": 1-2 sentences saying why the answer is right, "area": 2-4 word label for the sub-area the question comes from}.');
    p.push("Rules: facts must be correct and unambiguous. Distractors must be plausible. No 'all of the above' or 'none of the above'. Vary the question style (definition, scenario, compare, predict, spot the error). Keep each option under 20 words. Put the correct option at varied positions.");
    p.push("Variation seed (ignore its meaning, just let it change your picks): "+Math.random().toString(36).slice(2,9));
    if(prev.length){
      p.push("");
      p.push("Do NOT repeat or closely rephrase any of these earlier questions:");
      prev.forEach(function(q){ p.push("- "+q); });
    }
    return p.join("\n");
  }

  function parseQuiz(text){
    var a = text.indexOf("["), b = text.lastIndexOf("]");
    if(a<0||b<=a) throw new Error("no json");
    var arr = JSON.parse(text.slice(a,b+1));
    var out = [];
    arr.forEach(function(x){
      if(!x||typeof x.q!=="string"||!Array.isArray(x.options)||x.options.length!==4) return;
      var ans = Number(x.answer); if(!(ans>=0&&ans<=3)) return;
      var opts = x.options.map(function(o,i){ return {t:String(o), ok:i===ans}; });
      shuffle(opts);
      out.push({q:x.q, opts:opts, expl:String(x.explanation||""), area:String(x.area||"")});
    });
    if(out.length<5) throw new Error("too few");
    return out.slice(0,10);
  }

  var ERR = {
    not_granted:"Claude was not allowed for this page. Reload and allow it to generate quizzes.",
    sampling_disabled:"Claude is not available for this account.",
    rate_limited:"Too many requests or your usage limit was reached. Wait a bit, then try again.",
    session_expired:"Your session expired. Sign in again and reload.",
    refused:"Claude could not write a quiz for that topic. Try rewording it.",
    empty_completion:"No questions came back. Try a more specific topic.",
    upstream_error:"The connection dropped while writing. Try again."
  };

  /* ---------- generate ---------- */
  async function generate(){
    var topic = $("#topic").value.trim();
    var err = $("#seterr"); err.classList.add("hidden");
    if(!topic){ err.textContent="Enter a topic first."; err.classList.remove("hidden"); $("#topic").focus(); return; }
    S.topic = topic; S.desc = $("#desc").value; save();
    if(!sample){ err.textContent="Claude is not available in this view."; err.classList.remove("hidden"); return; }

    show("loading");
    $("#loadtitle").textContent = "Thinking...";
    $("#loadsub").textContent = "Writing your questions";
    $("#loadbar").style.width = "6%";
    S.ctl = new AbortController();
    try{
      var res = await sample(buildPrompt(), {
        signal:S.ctl.signal, cache:false, modelTier:"default",
        onText:function(o){
          var n = (o.text.match(/"q"\s*:/g)||[]).length;
          $("#loadtitle").textContent = "Writing question "+Math.min(Math.max(n,1),10)+" of 10";
          $("#loadbar").style.width = Math.min(100, 8+n*9)+"%";
        }
      });
      S.qs = parseQuiz(res.text);
      S.qs.forEach(function(q){ S.asked.push(q.q); });
      S.i = 0; S.picks = [];
      renderQ(); show("quiz"); $("#qtext").focus({preventScroll:true});
    }catch(e){
      show("setup");
      if(e && e.code==="cancelled") return;
      var msg;
      if(e && e.code) msg = ERR[e.code] || ERR.upstream_error;
      else msg = "The questions came back in a format the page could not read. Try again.";
      err.textContent = msg; err.classList.remove("hidden");
    }
  }

  /* ---------- quiz ---------- */
  function renderProgress(){
    var box = $("#progress"); box.textContent="";
    S.qs.forEach(function(q,i){
      var s = el("span");
      if(S.picks[i]!==undefined) s.className = q.opts[S.picks[i]].ok ? "ok":"no";
      else if(i===S.i) s.className="cur";
      box.appendChild(s);
    });
  }
  function renderQ(){
    var q = S.qs[S.i], picked = S.picks[S.i];
    renderProgress();
    $("#qcount").textContent = "Question "+(S.i+1)+" of "+S.qs.length;
    $("#qarea").textContent = q.area;
    $("#qtext").textContent = q.q;
    var ul = $("#opts"); ul.textContent="";
    q.opts.forEach(function(o,i){
      var li = el("li"), b = el("button","opt"); b.type="button";
      b.appendChild(el("b",null,LETTERS[i]));
      b.appendChild(el("span",null,o.t));
      if(picked!==undefined){
        b.disabled = true;
        if(o.ok) b.classList.add("right"); else if(i===picked) b.classList.add("wrong");
      }else{
        b.addEventListener("click", function(){ pick(i); });
      }
      li.appendChild(b); ul.appendChild(li);
    });
    var ex = $("#explain"), nx = $("#next");
    if(picked!==undefined){
      ex.textContent=""; 
      var correct = q.opts[picked].ok;
      ex.appendChild(el("strong",null, correct ? "Correct. " : "Not quite. "));
      ex.appendChild(document.createTextNode(q.expl));
      ex.classList.remove("hidden");
      nx.textContent = S.i===S.qs.length-1 ? "See results" : "Next question";
      nx.classList.remove("hidden");
    }else{
      ex.classList.add("hidden"); nx.classList.add("hidden");
    }
  }
  function pick(i){ S.picks[S.i]=i; renderQ(); $("#next").scrollIntoView({block:"nearest",behavior:"smooth"}); }
  function next(){
    if(S.i<S.qs.length-1){ S.i++; renderQ(); window.scrollTo(0,0); $("#qtext").focus({preventScroll:true}); }
    else finish();
  }

  /* ---------- result ---------- */
  function finish(){
    var score = S.qs.reduce(function(n,q,i){ return n + (q.opts[S.picks[i]].ok?1:0); },0);
    var sc = $("#score"); sc.textContent = score; sc.appendChild(el("small",null," / "+S.qs.length));
    var v = score>=9?"Excellent." : score>=7?"Solid work." : score>=5?"Getting there." : "Worth another pass.";
    $("#verdict").textContent = v;
    $("#rsub").textContent = S.topic+" · level "+S.level+" · "+DEPTH[depthOf(S.desc)].name.toLowerCase();
    var rv = $("#review"); rv.textContent="";
    S.qs.forEach(function(q,i){
      var ok = q.opts[S.picks[i]].ok;
      var d = el("details","rv"), s = el("summary");
      s.appendChild(el("span","mk "+(ok?"ok":"no"), ok?"✓":"✗"));
      s.appendChild(el("span",null,(i+1)+". "+q.q));
      d.appendChild(s);
      var b = el("div","rvbody");
      var right = q.opts.filter(function(o){return o.ok;})[0];
      if(!ok) b.appendChild(el("p",null,"Your answer: "+q.opts[S.picks[i]].t));
      b.appendChild(el("p",null,"Correct answer: "+right.t));
      b.appendChild(el("p",null,q.expl));
      d.appendChild(b); rv.appendChild(d);
    });
    show("result");
  }

  /* ---------- wire up ---------- */
  $("#desc").addEventListener("input", updateDepth);
  $("#go").addEventListener("click", generate);
  $("#stop").addEventListener("click", function(){ if(S.ctl) S.ctl.abort(); });
  $("#next").addEventListener("click", next);
  $("#again").addEventListener("click", generate);
  $("#change").addEventListener("click", function(){ S.asked=[]; show("setup"); });
  $("#topic").addEventListener("keydown", function(e){ if(e.key==="Enter") generate(); });

  restore(); buildDial(); updateDepth();

  (async function(){
    try{ sample = window.claude ? await window.claude.use("sample") : null; }catch(e){ sample = null; }
  })();
})();
