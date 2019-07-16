# 09 동적 계획법 테크닉
## 9-1 최적화 문제의 실제 답 계산하기
#### 예제: 최대 증가 부분 수열 실제로 출력하기
8장에서 LIS의 길이를 구하는 문제를 풀었다. 하지만 이것으로 어떤 원소들로 구성되어 있는지 알 수 없다. 최적해를 생성해 내는 아래 코드를 보자.
``` c++
// 최대 증가 부분 수열(LIS)을 실제로 계산하기
int n;
int cache[101],S[100],choices[101];
//S[start]에서 시작하는 증가 부분 수열 중 최대 길이를 반환한다.
int lis4(int start){
    int& ret = cache[start+1];
    if(ret != -1) return ret;
    //항상 S[start]는 있기 때문에 길이는 최하 1
    ret = 1;
    int bestNext = -1;
    for(int next = start+1; next<n; ++next)
        if(start == -1 || S[start] < S[next]) {
            int cand = lis4(next)+1;
            if(canf>ret){
            ret = cand;
            bestNext = next;
            }
        }
    choices[start+1] = bestNext;
    return ret;
}
// S[start]에서 시작하는 LIS를 seq에 저장한다.
void reconstruct(int start, vector<int>& seq) {
    if(start != -1) seq.push_back(S[start]);
    int next =choices[start+1];
    if(next != -1) reconstruct(net,seq);
}
```
#### 최적화 문제 답 계산하기 레시피
1. 재귀 호출의 각 단계에서 최적해를 만들었던 선택을 별도의 배열에 저장해 둔다.
2. 별도의 재귀 함수를 이용해 이 선택을 따라가며 각 선택지를 저장하거나 출력한다.
## 9-2 여행 짐 싸기(난이도 : 중)
#### 여행 가방을 싸려고 하는데 각 물건에 대한 부피와 절박도가 주어진다. 물건들의 부피 합은 캐리어의 용량 W 이하여야 하며 절박도를 최대화할 수 있는 물건들의 목록을 계산해라
