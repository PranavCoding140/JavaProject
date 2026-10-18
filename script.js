// ---------- Global variables (shared by all functions) ----------
var questions = [];   // array of 10 question objects
var current = 0;      // index of the question being shown
var score = 0;        // number of correct answers

// ---------- Show only one screen at a time ----------
function show(id) {
  var screens = ["setup", "loading", "quiz", "result"];
  for (var i = 0; i < screens.length; i++) {
    document.getElementById(screens[i]).classList.add("hidden");
  }
  document.getElementById(id).classList.remove("hidden");
}

// ---------- Create the instruction text we send to Claude ----------
function buildPrompt(topic, difficulty) {
  return "Write 10 multiple choice questions on the topic: " + topic + ". " +
         "Difficulty level: " + difficulty + ". " +
         "Return ONLY a JSON array and no other text. " +
         "Each item must look like " +
         "{\"q\":\"question\",\"options\":[\"a\",\"b\",\"c\",\"d\"],\"answer\":0,\"explanation\":\"why\"} " +
         "where answer is the index (0 to 3) of the correct option.";
}

// ---------- Step 1: ask the server for a quiz ----------
async function startQuiz() {
  var topic = document.getElementById("topic").value.trim();
  var difficulty = document.getElementById("difficulty").value;

  if (topic === "") {
    alert("Please enter a topic first.");
    return;
  }

  show("loading");

  try {
    // send the prompt to our Java server and wait for the reply
    var response = await fetch("/api/quiz", {
      method: "POST",
      body: buildPrompt(topic, difficulty)
    });
    var text = await response.text();

    if (!response.ok) {
      throw new Error(text);
    }

    // The reply is text. Cut out the part between [ and ] and convert it to an array.
    var start = text.indexOf("[");
    var end = text.lastIndexOf("]");
    questions = JSON.parse(text.substring(start, end + 1));

    current = 0;
    score = 0;
    showQuestion();
    show("quiz");
  } catch (error) {
    alert("Could not create the quiz: " + error.message);
    show("setup");
  }
}

// ---------- Step 2: display one question ----------
function showQuestion() {
  var q = questions[current];

  document.getElementById("count").textContent =
      "Question " + (current + 1) + " of " + questions.length;
  document.getElementById("question").textContent = q.q;
  document.getElementById("explanation").textContent = "";
  document.getElementById("next").classList.add("hidden");

  var box = document.getElementById("options");
  box.innerHTML = "";   // remove old buttons

  // make one button for each option
  q.options.forEach(function (optionText, index) {
    var btn = document.createElement("button");
    btn.textContent = optionText;
    btn.className = "option";
    btn.onclick = function () {
      checkAnswer(index);
    };
    box.appendChild(btn);
  });
}

// ---------- Step 3: check the chosen answer ----------
function checkAnswer(chosen) {
  var q = questions[current];
  var correct = Number(q.answer);
  var buttons = document.querySelectorAll(".option");

  // stop the user from clicking again
  buttons.forEach(function (b) {
    b.disabled = true;
  });

  buttons[correct].classList.add("right");   // always show the right answer in green

  if (chosen === correct) {
    score++;
  } else {
    buttons[chosen].classList.add("wrong");  // wrong choice in red
  }

  document.getElementById("explanation").textContent = q.explanation;

  var nextBtn = document.getElementById("next");
  nextBtn.textContent = (current === questions.length - 1) ? "See Result" : "Next";
  nextBtn.classList.remove("hidden");
}

// ---------- Step 4: next question or final result ----------
function nextQuestion() {
  current++;
  if (current < questions.length) {
    showQuestion();
  } else {
    document.getElementById("scoreText").textContent =
        "You scored " + score + " out of " + questions.length;
    show("result");
  }
}

// ---------- Connect buttons to functions ----------
document.getElementById("startBtn").onclick = startQuiz;
document.getElementById("next").onclick = nextQuestion;
document.getElementById("restart").onclick = function () {
  show("setup");
};
