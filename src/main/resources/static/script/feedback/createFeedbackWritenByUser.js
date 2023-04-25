

async function feedbackSpecificProductList(api, status) {
  try {
    
    feedbackSpecificContainer.innerHTML = ``;
    let string = status.innerText;
    if (string != 'FAIL') {
      if (api.length != 0){
        for (let i = 0; i < api.length; i++) {
          feedbackSpecificContainer.innerHTML += `
          <article class="FeedbackWritenByUser">
            <div class="FeedbackPictureByUser">
            <img src="http://localhost:8080${api[i].imgUrl}" style="max-width:80%; min-height:100px;"/>
            </div>
            <div>
              <div class="FeedbackScore-FeedbackUser">
                <h6>작성자:  ${api[i].userName} score: ${api[i].score}</h6>  
                <h6 class="Score"> </h6>
              </div>
              <div class="FeedbackText">
                <h4>피드백 텍스트 : </h4>
                <h6>${api[i].feedbackText} </h6>
              </div>
            </div>
          </article>
          `
        }
      } else {
        feedbackSpecificContainer.innerHTML += `
      <article class="FeedbackWritenByUser">
        <div class="FeedbackPictureByUser">
        <img src="http://localhost:8080/file/default/nofeedback.png" style="max-width:80%; min-height:100px;"/>
        </div>
        <div class="FeedbackScore-FeedbackUser">
          <h4>작성된 상품 후기가 없습니다. 후기를 작성해 주세요</h4>  
        </div>
      </article>
      `
      }
    } else {
      feedbackSpecificContainer.innerHTML += `
      <article class="FeedbackWritenByUser">
        <div class="FeedbackPictureByUser">
        <img src="http://localhost:8080/file/default/investfail.png" style="max-width:80%; min-height:100px;"/>
        </div>
        <div class="FeedbackScore-FeedbackUser">
          <h1>투자에 실패한 상품 입니다</h1>  
        </div>
      </article>
      `
    }
  } catch {
    console.log("fail to create list")
  } finally {
    console.log("create list complete")
    averageScoreOfFeedback(api)
  }
}

async function averageScoreOfFeedback(api) {
  try {
    let score = document.querySelectorAll('.Score');
    let statusForFunction = document.querySelector('.FeedbackStatus');
    let averageScoreOfProduct = document.querySelector('.FeedbackScore');
    let totalScore = 0;
    if (statusForFunction == 'FAIL') {
      averageScoreOfProduct.innerText = "상품 점수 : 0";
    } else {
      if (score.length != 0 ){
        for (let i = 0; i < api.length -1; i++) {
        totalScore += api[i].score;
        }
        let AverageScore = Math.round(totalScore / api.length); 
        averageScoreOfProduct.innerText = `상품 평균 점수 : ${AverageScore}`;

      } else {
        averageScoreOfProduct.innerText = "상품 평균 점수 : 0";
      }
    }
    
  } catch {
    console.log("fail to get average score")
  } finally {
    console.log("create average score")
  }
}



function createSpecificFeedbackList(status) {
  fetch(`/api/feedback/specificlist/?num=${productNumber}`)
  .then(res => res.json())
  .then(data => feedbackSpecificProductList(data, status))
  .then(data => (averageScoreOfFeedback(data)))
}


createSingleFeedback();


