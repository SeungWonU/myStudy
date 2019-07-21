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
## 9-2 문제 : 여행 짐 싸기(난이도 : 중)
#### 여행 가방을 싸려고 하는데 각 물건에 대한 부피와 절박도가 주어진다. 물건들의 부피 합은 캐리어의 용량 W 이하여야 하며 절박도를 최대화할 수 있는 물건들의 목록을 계산해라
## 9-3 풀이
#### 최적화 문제를 위한 동적 계획법 레시피를 떠올리면 items를 넣고 남은 용량에 담을 수 있는 물건들의 절박도 합만을 반환하도록 pack()을 만든다.
pack(capacity,item) = 캐리어에 용량이 capacity만큼 남았을 때 item 이후의 물건들을 싸서 얻을 수 있는 절박도
#### 여기서 물건에 대한 선택을 살펴보자
가져갈 경우 : pack(capacity - volume[item],item+1) + need[item]
가져가지 않을 경우 : pack(capacity,item+1)
``` c++
// 여행 짐싸기 문제를 해결하는 동적 계획법 알고리즘
int n, capacity;
int volume[100], need[100];
int cache[1001][100];
string name[100];
//캐리어에 남은 용량이 capacity일 때 item 이후의 물건들을 얻을 수 있는 최대 절박도 합을 반환
int pack(int capacity,int item){
    //기저 사례 : 더 담을 물건이 없을 때
    if(item == n) return 0;
    int& ret = cache[capacity][item];
    if(ret !=-1) return ret;
    //이 물건을 담지 않을 경우
    ret = pack(capacity,item+1);
    if(capacity >= volume[item])
        ret = max(ret, pack(capacity = volume[item], item+1) + need[item]);
    return ret;
}
```
## 9-6 k번째 답 계산하기
* ### 예제 : 모스 부호 사전(난이도 : 중)
#### 모스 부호(Morse code)는 무선 전신에 주로 사용하던 코드로, 짧은 신호(단점,o)와 긴 신호(장점,-)를 섞어 글자를 표현하는 방식이다. n개의 장점과 m개의 단점으로 구성된 모든 신호들을 담고 있는 사전이 있다고 하자.
-와 o의 순서 : -의 아스키 코드가 더 작기 때문에 먼저 온다. 문제 : k번째 신호를 출력하는 프로그램을 작성해라
#### 모든 신호 만들기 : 완전 탐색 알고리즘을 사용하자!
``` c++
// 모든 모스 신호를 만드는 완전 탐색 알고리즘
// s : 지금까지 만든 신호    n : 더 필요한 -의 개수    m : 더 필요한 o의 개수
void generate(int n, int m, string s){
    // 기저 사례 : n=m=0
    if(n == 0 && m == 0){
        cout << s <<endl;
        return;
        }
    if(n>0) generate(n-1,m,s+"-");
    if(m>0) generate(n,m-1,s+"o");
}
```
#### k-1개 건너뛰기 : 다음 단계는 모든 신호를 출력하는 대신 k번째 신호만 출력하는 코드를 작성한다. 사전순으로 모든 신호를 생성하면서 k-1개를 건너뛰고 첫 번째 것을 출력하는 코드를 작성하면 된다.
``` c++
// k-1개 건너뛰고 첫 번째 신호를 출력하는 알고리즘
int skip;
// skip개를 건너뛰고 출력한다.
void generate2(int n, int m, string s) {
    // 기저 사례 : skip < 0
    if(skip < 0) return;
    // 기저 사례 : n=m=0
    if(n == 0 && m == 0){
        //더 건너뛸 신호가 없는 경우
        if(skip == 0) cout << s << endl;
        --skip;
        return;
    }
    if(n>0) generate(n-1,m,s+"-");
    if(m>0) generate(n,m-1,s+"o");
}
```
#### 좀더 똑똑하게 건너뛰기 : k가 크다면 위 코드로 작성하기 힘들다.
## 9-9 문제 : 드래곤 커브(난이도 : 중)
#### 드래곤 커브는 선분 하나에서 시작해서 간단한 규칙으로 이 선분을 변형해서 만들어지며, 변형이 한 번 이루어져 세대가 변할때마다 더 복잡한 모양으로 진화한다.
일부를 확대했을 때 전체와 비슷한 형태로 구성된 도형들을 프랙탈(fractal)이라고 한다.드래곤 커브를 그리는 방법을 드래곤 커브 문자열이라고 부른다. 문자열은 X Y F + -로 구성된 문자열인데, 한 점에서 시작해 커브를 그리면 된다.
## 9-10 풀이
#### p번째 글자를 찾는 함수 작성하기 : 우선 맨 앞에서부터 전체 문자열을 생성하는 알고리즘을 만들고 이것을 기반으로 p번째 글자를 찾아보자.
> curve(seed,generations) = 초기 문자열 seed를 generations세대 진화시킨 결과를 출력한다.
``` c++
//드래곤 커브 문자열을 생성하는 재귀 호출 알고리즘
//초기 문자열 seed를 generations세대 진화한 결과를 출력
void curve(const string seed, int generations) {
    //기저 사례
    if(generations == 0)
{    cout << seed;
    return;
}
    for(int i=0;i<seed.size();i++){
    if(seed[i] == 'X')
        curve("X+YF",generations-1);
    else if(seed[i] == 'Y')
        curve("FX-Y",generations-1);
    else
        cout << seed[i];
    }
}
```
#### 이제 건너뛰어야 하는 글자의 수 skip을 전역 변수에 유지하면서, 문자열을 출력할 때마다 skip과 출력할 부분을 비교한다. 출력할 문자열의 길이가 skip보다 작거나 같으면 출력하지 않고 skip을 감소시킨 뒤 진행한다.
#### 계산 결과 길이 미리 계산하기 : p번째 글자를 출력하는 재귀 호출 코드를 최적화하려면, 재귀 호출할 때마다 이 재귀 호출이 몇 글자를 출력할지를 미리 알고 skip과 비교해야 한다.
## 9-11 정수 이외의 입력에 대한 메모이제이션
#### 연관 배열 사용하기 : STL의 map과 연관 배열을 사용해 캐시를 구현한다. map<vector<int>,int> cache; (시간이 오래걸림)
#### 일대일 대응 함수 작성하기 : 입력을 적절하게 정수로 변환해 줄 수 있는 함수로 작성한다.예를 들어 정수 배열이 항상 [1,n]범위의 수를 하나씩 가지고 있다면
입력의 종류는 n!가지가 된다. 입력 배열이 n!개의 가능한 입력 중 사전순으로 몇 번째인지 반환하는 함수를 만들어 메모이제이션을 할 수 있다.
``` c++
int cache[N_FACTORIAL];
// N!개의 입력 중 key가 몇 번째에 해당하는지 반환한다.
int mapFunc(const vector<int>& key);
int f(const vector<int>& key){
    //기저 사례를 처리한다
    ...
    // 메모이제이션
    int& ret = cache[mapFunc(key)];
    if(ret !=-1) return ret;
    ...
    return ret;
}
```
#### mapFunc()의 값을 cache[]의 인덱스로 사용하는 테크닉은 일대일 대응 함수(bijection function)를 기초로 한다. 일대일 대응이란 두 원소가 서로 1:1대응해 주는 함수를 얘기한다. mapFunc()은 정수의 배열 input을 [0,n!-1]범위의 자연수로 대응한다.그렇다면 왜 일대일 대응 함수로 해야 할까?
* #### 만약 두개의 다른 입력 a,b에 대해 mapFunc()의 반환 값이 같다면 프로그램은 잘못된 답을 반환할 수도 있다.f(a)가 호출되어서 a에 대한 계산 값이 테이블에 저장된 뒤, f(b)가 호출된다면 a에 대한 값을 반환해 버리기 때문이다.
* #### 어떤 배열 위치에 대응되는 입력이 없다면, 해당 위치는 버려지게 되어 필요한 것보다 많은 메모리를 쓰게 된다.(메모리 양이 애초에 작을 경우 상관없다)
#### 입력이 불린 값의 배열인 경우 : 불린 값 변수는 참 or 거짓 값만 가지므로 배열의 길이가 n이라고 할 때 존재할 수 있는 입력의 수는 2^n가지가 된다. 이때 길이가 n인 배열을 길이가 n인 2진수로 보는 것이다. 배열 A에 대해 A[0]은 맨 아래, A[1]은 그 다음, A[n-1]은 가장 윗자리로 본다. 이때 해당 위치에 참이면 1을 가리킨다. 이 경우 사용하는 메모리의 양이 2^n에 비례해서 n이 굉장히 작을 때 이 방법을 추천한다.
* ### 예제 : 여행하는 외판원 문제
shortestPath(path) = 지금까지 만든 경로가 path에 주어질 때, 이 경로를 연장해 나머지 도시들을 방문하는 모든 경로 중 최소 길이를 반환한다.
#### 함수의 정의 바꾸기 : 메모이제이션을 하기 위해서 지금까지 한 선택들에 대한 정보를 최소한만 받도록 함수의 정의를 고쳐야 한다.
1. 방문한 경로의 길이 계산 : shortestPath()가 전체 경로의 길이 대신 남은 경로의 최소 길이를 반환하도록 바꾼다.
2. 어떤 도시를 전에 방문한 적이 있는지 확인 : 도시를 방문해쓴지 여부는 꼭 확인해야 하지만 어떤 순서대로 방문했는지는 알 필요가 없다. n길이의 불린 값 배열만 전달한다.
> shortestPath2(here,visited) = 현재 위치가 here이고 각 도시들을 방문한 적이 있는지 여부가 불린 값 배열 visited에 주어질 때,here에서 시작해 나머지 도시를 방문하는 부분 경로의 최소를 반환한다.
#### 메모이제이션하기 : 길이가 정해진 불린 값의 배열을 정수형으로 표현하면 쉽게 메모이제이션을 적용할 수 있다.
``` c++
int n, dist[MAX][MAX];
double cache[MAX][1<<MAX];// -1로 초기화해 둔다.
// here : 현재 위치
// visited : 각 도시의 방문 여부
// here에서 시작해 남은 도시들을 방문할 수 있는 최단 경로의 길이를 반환한다.
// 나머지 도시들을 모두 방문하는 경로들 중 가장 짧은 것의 길이를 반환한다.
// 항상 0번 도시에서 시작한다고 가정한다.
double shortestPath2(int here, int visited){
    // 기저 사례 : 모든 도시를 다 방문했을 때는 0번 도시로 돌아가고 종료한다.
    if(visited == (1<<n)-1) return dist[here][0];
    // 메모이제이션
    double& ret = cache[here][visited];
    if(ret >= 0) return ret;
    ret = INF; //매우 큰값으로 초기화
    // 다음 방문할 도시를 전부 시도해 본다.
    for(int next =0; next < n; ++next){
        //이미 방문한 도시인 경우
        if(visited& (1<<next)) continue;
        double cand = dist[here][next]+ shortestPath2(next, visited + (1<<next));
        ret min(ret,cand);
    }
    return ret;
}
