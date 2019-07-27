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
```
## 9-14 문제 : 실험 데이터 복구하기(난이도 : 중)
#### 주어진 문자열 조각들을 모두 부분 문자열로 포함하는 문자열 중 가장 짧은 것을 반환해라.
#### 답의 구조 파악하기 :
1. 어떤 문자열 조각에도 포함되지 않는 문자를 더할 일은 없다.
2. 연속해서 출현하는 문자열들의 접미사와 접두사를 최대한 많이 겹치게 해야한다.
3. 한 문자열 조각이 다른 문자열에 포함될 경우 무시한다.
#### 모든 답 만들어 보기: 문자열을 만드는 좋은 방법은 문자열 조각들이 어떤 순서로 출현할지를 정하는 것이다. 각 조각을 최대한 겹치도록 연결해야 한다. a와b의 최대 몇 글자가 겹치는지 계산해 주는 overlap(a,b)가 있다고 하자.  문자열이 w1,w2,w3 ...wk 일때 w의 합 - overlap의 합을 구하면 원하는 문자열의 길이가 나온다.
외판원 문제와 비슷하게 접근해 보면
* restore(last,used) = 마지막에 출현한 조각 last와 지금까지 출현한 조각의 집합 used가 주어질 때, 나머지 조각을 추가해서 얻을 수 있는 overlaps()의 최대 합
restore(last,used) = max(overlap(last,next)+restore(next,used U {next}))
#### 최적해의 점수 계산하기 : overlap 과 restore 의 구현을 보여준다.
``` c++
// 실험 데이터 복구 문제를 해결하는 동적 계획법 알고리즘
const int MAX_N = 15;
int k;
string word[MAX_N];
int cache[MAX_N][1<<MAX_N], overlap[MAX_N][MAX_N];
int restore(int last,int used) {
//기저 사례
if(used == (1<<k)-1) return 0;
//메모이제이션
int& ret = cache[last][used];
if(ret != -1) return ret;
ret = 0;
for(int next=0; next<k; ++next)
    if((used& (1<<next)) == 0 ){
        int cand = overlap[last][next] + restore(next,used+(1<<next));
        ret = max(ret,cand);
    }
    return ret;
}
```
#### 이 코드는 O(k*2^k)개의 부분 문제에 대한 답을 각각 O(k)의 시간을 들여 계산하기 때문에 전체 시간 복잡도는 O(k^2*2^k)가 된다.
#### 실제 문제의 답인 문자열을 찾기 : 아래 코드는 실제 문자열을 구현한 것이다. 다음 사항들을 유의해서 보자.
1. restore()가 각 경우 최선의 선택을 별도의 배열에 기록해 두지 않았기 때문에, reconstruct()는 다음에 나와야 할 문자열 조각이 무엇인지를 직접 찾는다.
2. 우리 코드가 정확하다면 다음에 나올 문자열 조각을 찾을 수 없는 경우는 없을 것이다. reconstruct()는 꼭 무언가를 반환해야 하므로 특수한 값을 리턴한다.(return ****ooops****)눈에 띄게 한다.
``` c++
// 처음 호출시 last는 recover()가 최댓값을 반환한 시작 단어로
// used는 1<<last로 둔다.
string reconstruct(int last, int used) {
//기저 사례
if(used == (1<<k)-1) return "";
//다음에 올 문자열 조각을 찾는다.
for(int next = 0; next< k; ++next) {
    //next가 이미 사용되었으면 제외
    if(used & (1<<next)) continue;
    //next를 사용했을 경우의 답이 최적해와 같다면 next를 사용한다.
    int ifUsed = restore(next, used + (1<<next)) + overlap[last][next];
    if(restore(last,used) == ifUsed)
        return (word[next].substr(overlap[last][next]) + reconstruct(next,used+(1<<next));
}
return "****ooops******";
}
```
이 코드는 문자열 조각 하나를 연결할 때마다 O(k)시간이 걸리는 반복문을 수행하므로 전체는 O(k^2)가 된다.
## 9-16 조합 게임
#### 동적 계획법의 또 다른 사용처는 여러 조합 게임(combinatorial game)을 해결하는 것이다. 조합 게임이란 체스나 바둑처럼 두 참가자가 하는 게임을 가리킨다. 게임 트리를 보면 완벽한 수의 개념을 쉽게 이해할 수 있다. 게임 트리의 맨 아래줄부터 시작하면 완벽하게 풀 수 있다. 하지만 그 전에 승부가 나거나 메모리도 많이 차지하기 때문에 위에서부터 내려오는(top down)재귀 호출 알고리즘을 사용한다.
* winner(state,player) = 게임의 현재 상태가 state이고, player가 수를 둘 차례일 때 어느 쪽이 최종적으로 이길까?
player 인자를 통해 누가 둘 차례인지 결정하는데 다음과 같이 정의하면 이런 정보가 필요 없다.
* canWin(state) = 게임의 현재 상태가 state일 때, 이번에 수를 둘 차례인 참가자가 이길까?
#### canWin은 이 상태에서 둘 수 있는 수를 하나 하나 순회하며, 해당 수를 둔 후의 상태 state에 대해 canWin을 호출한다. 이때 true가 되면 다음 차례 사람이 이기고, false라면 지금 한 내가 이기는 것이다.
* ### 예제 : 틱택토(난이도 : 하)
#### 상태 표현 : 틱택토는 3X3 크기의 3목 게임이다. O와 X로 자신의 말을 표현한다.
* canWin(board) = 틱택토 게임판이 현재 board일 때 이번 차례인 사람이 이길 수 있는지 반환한다.
#### 이 함수에 메모이제이션을 적용하려면 map에 곧장 vector<string>을 넣거나 게임판을 정수로 변환해 주는 일대일 함수를 구현해야 한다. 일대일 함수는 board를 아홉자리의 3진수 숫자로 보는 것이다.
#### 비기는 게임 : 오목과 달리 비기는 경우가 있다.
canWin(board) = 틱택토 게임판이 현재 board일 때 이번 차례인 사람이 이길 수 있으면 1을, 비길 수 있으면 0을, 질 수밖에 없으면 -1을 반환한다.
#### 구현 : 이것을 유의해서 보자
1. 이미 게임이 끝난 상태를 기저 사례로 한다. 처음 받은 게임판에 한 줄이 만들어져 있다면 마지막에 둔 사람이 승리한 것이니 이번 차례인 사람은 패배하게 된다.
2. 함수의 반환 값이 -1이 될 수도 있기 때문에 cache[]의 초기값은 -2dlek.
3. canWin에 현재 게임판의 상황 말고도 이번이 누구 차례인지를 turn으로 전달한다. 틱택토는 항상 X가 먼저 두기 때문에 이 정보는 board만 가지고도 알 수 있다만, 이렇게 하면 구현이 더 간단해진다. 사실 turn은 메모이제이션에 포함되지 않는다.
4. canWin은 모든 수를 시도해 보며 반환 값 중 가장 작은 것을 찾는다.어떤 수를 두었을 때 -1 반환되었다면 내가 이길 수 있다는 뜻이고, -1이 없고 0이 있으면 비길 수 있기 떄문이다.
``` c++
// turn이 한 줄을 만들었는지를 반환한다.
bool isFinished(const vector<string>& board, char turn);
// 틱택토 게임판이 주어질 때 [0,19682] 범위의 정수로 변환한다.
int bijection(const vector<string>& board) {
    int ret = 0;
    for(int y=0; y<3; y++)
        for(int x=0; x<3; x++) {
            ret = ret * 3;
            if(board[y][x] == 'o') ++ret;
            else if(board[y][x] == 'x') ret +=2;
        }
    return ret;
}
// cache[]는 -2로 초기화한다.
int cache[19683];
// 내가 이길 수 있으면 1을, 비길 수 있으면 0을, 지면 -1을 리턴한다.
int canWin(vector<string>& board, char turn) {
    // 기저 사례 : 마지막에 상대가 둬서 한 줄이 만들어진 경우
    if(isFinished(board, 'o'+'x'-turn)) return -1;
    int& ret = cache[bijection(board)];
    if(ret != -2) return ret;
    //모든 반환 값을 min을 취하자.
    int minValue = 2;
     for(int y=0; y<3; y++)
            for(int x=0; x<3; x++)
               if(board[y][x] == '.') {
                board[y][x] == turn;
                minValue = min(minValue, canWin(board, 'o'+'x'-turn));
                board[y][x] = '.';
               }
        // 플레이할 수 없거나, 어떻게 해도 비기는 것이 최선인 경우
        if(minValue == 2 || minValue == 0) return ret =0;
        // 최선이 상대가 이기는 거라면 난 무조건 지고, 상대가 지는 거라면 난 이긴다.
        return ret = -minValue;
}
```
## 9-17 문제 : 숫자 게임(난이도 : 하)
#### n개의 정수를 일렬로 늘어놓은 게임판을 가지고 게임을 한다.
## 풀이
#### 이 문제는 승패도 중요하지만 얼마나 큰 점수 차이로 승부가 나는지가 중요하다.
* play(state) = 현재 게임판에 남은 수들이 state일 때(이번 차례인 참가자의 점수) - (다른 참가자의 점수)의 최대치
#### 상태 표현 :
play(left,right) = max( board[left]-play(left+1,right), board[right]-play[left,right-1],-play(left+2,right),-play(left,right-2))
#### play는 재귀 호출이 이루어지고 board는 게임판의 각 수를 저장하는 배열이다.
``` c++
// 숫자 게임 문제를 해결하는 동적 계획법 알고리즘
const int EMPTY = -9123123;
int n,board[50];
int cache[50][50];
int play(int left, int right) {
// 기저 사례 : 모든 수가 다 없어졌을 때
if(left > right) return 0;
int& ret = cache[left][right];
if(ret != EMPTY) return ret;
//수를 가져가는 경우
ret = max(board[left]-play(left+1,right), board[right]-play(left,right-1));
//수를 없애는 경우
if(right - left +1 >= 2) {
    ret = max(ret, -play(left+2,right));
    ret = max(ret, -play(left,right-2));
}
return ret;
}
```
이 알고리즘은 O(n^2)의 시간이 걸린다.또한 인공지능 과목에서 배우는 미니맥스 알고리즘과 본질적으로 같다. 재귀함수의 정의를 다음과 같이 바꿔보자.
* play(state,player) = 현재 게임판에 남은 수들이 state라고 가정하고, 이번 차례가 player라고 할때 (현우점수) - (서하 점수)
#### 그러면 현우는 play의 반환 값을 가능한 한 최대화하고, 서하는 최소화하는 쪽으로 게임을 하게된다.이것을 미니맥스라고 부른다.
## 9-19 문제 : 블록 게임(난이도 : 중)
#### 5X5 크기의 게임판에서 시작해 둘이 번갈아 가며 블록을 하나씩 게임판에 내려놓는다. L모양의 3칸짜리 블록과 2칸자리 블록이 있으며, 항상 게임판에 있는 줄에 맞춰 놓아야 한다.더 올려놓을 곳이 없다면 게임은 끝이난다.
## 풀이
#### 상태 표현하기 : 이 게임은 양쪽이 둘 수 있는 수가 항상 같은 대칭 게임(impartial game)이다.
* play(board) = 현재 게임판의 상태가 board일 때 이번 차례인 사람이 이길 방법이 있는지를 반환한다.
#### 구현 : 이 코드를 비트 마스크를 사용해 더 간단하게 할 수 있다.
## 9-21 반복적 동적 계획법
#### 재귀 함수가 아니라 반복문을 이용해서 동적 계획법을 구현하는 기법을 반복적(iterative) 동적 계획법이라고 한다.
* ### 예제 : 삼각형 위의 최대 경로
#### 재귀 호출이 아닌 반복적 동적 계획법으로 푼 삼각형 최대 경로 구하기
``` c++
int n,triangle[100][100];
int C[100][100];
int iterative() {
  // 기저 사례를 계산한다.
  for(int x=0; x<n; ++x)
    C[n-1][x] = triangle[n-1][x];
  // 다른 부분을 계산한다.
  for(int y = n-2;y >=0; --y)
    for(int x=0;x<y+1;++x)
        C[y][x] = max(C[y+1][x],C[y+1][x+1]) + triangle[y][x];
  return C[0][0];
}
```
#### 슬라이딩 원도를 이용한 공간 복잡도 줄이기 : 슬라이딩 윈도란 사용하는 데이터 전체를 메모리에 유지하는 것이 아니라 필요한 부분만을 저장하는 기법을 가리킨다. 이 문제에 적용해 보면, C[i]를 계산하려면 C[i+1]만 필요하기 때문에 모든 메모리가 필요하지 않고 두 줄만 있으면 된다.
* ### 행렬 거듭제곱을 이용한 동적 계획법
#### 한정된 경우에만 쓰는 반복적 동적 계획법을 이용한 트릭으로, 선형 변환(linear transform)형태의 점화식을 행렬을 이용해 빠르게 푸는 기법이 있다.
이 기법은 피보나치 수열의 특정 항을 구하는 문제로 예를 든다. 피보나치 수열은 0과 1이 첫 숫자이고 다음부터는 이전 두수의 합으로 정의되는 무한수열이다.
fib(n) = fib(n-1) + fib(n-2)
#### 피보나치 수열의 n번째 숫자의 마지막 다섯자리를 계산하는 함수 fib(n)을 만들어 보자. 이것을 빨리 계산하려면 빠른 행렬 제곱 알고리즘을 이용해야 한다.
fib(i-1)과fib(i)를 포함하는 크기가 2인 열백터를 C 라고 하자
* ### 반복적 vs 재귀적 비교
#### 재귀적 동적 계획법의 장단점 :
1. 장점 : 직관적인 코드를 짠다 / 부분 문제 간의 의존 관계나 계산 순서에 대해 고민할 필요가 없다. / 전체 부분 문제중 일부의 답만 필요할 경우 좋다.
2. 단점 : 슬라이딩 윈도 기법을 쓸 수 없다. / 스택 오버플로를 조심해야 된다.
#### 반복적 동적 계획법의 장단점 :
1. 장점 : 구현이 대개 짧다 / 재귀 호출에 필요한 부하가 없기 때문에 좋다 / 슬라이딩 윈도 기법 사용 가능
2. 단점 : 구현이 비직관적이다 / 부분 문제 간의 의존 관계를 고려해 계산되는 순서를 고민해야 한다.

