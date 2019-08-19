# 11 조합 탐색
## 11-1 도입
#### 완전 탐색을 포함해, 유한한 크기의 탐색 공간을 뒤지면서 답을 찾아내는 알고리즘을 조합 탐색(combinational search)라고 부른다. 이 장에서는 조합 탐색 최적화 기법을 크게 두 가지로 분류한다.
* 가지치기(pruning) 기법은 탐색 과정에서 최적해로 연결될 가능성이 없는 부분들을 잘라내는 것이다. 현 상태에서 답의 나머지를 완성했을 때 얻을 수 있는 가장 좋은 답이 지금까지 알고있는 최적해보다 나쁘다면 탐색을 할 필요가 없다.
* 좋은 답을 빨리 찾아내는 기법들은 탐색의 순서를 바꾸거나, 탐색 시작 전에 탐욕법을 이용해 좋은 답을 찾아낸다.
## 11-2 조합 탐색 기법들
### 조합 탐색 뼈대의 구현
#### 6.7 shortestPath()는 경로의 길이를 직접 반환하지만, search()는 경로의 길이를 전역 변수 best에 저장하여 갱신한다. 이렇게 전역 변수에 지금까지 찾은 최적해의 길이를 저장해 두면 탐색 중에 더 최적해를 발견할 가능성이 없는 가지를 쳐내기 쉽다.
하지만 이와 같이 단순한 알고리즘은 시간이 오래걸린다. 따라서 우리는 동적 계획법보다 좋은 알고리즘을 만들기 위해 이걸 최적화 해서 조합 탐색을 하게 된다.
### 간단한 휴리스틱 가지치기
#### 한 상태가 주어질 때 아직 남은 도시들을 방문하기 위한 경로가 얼마나 길지를 적당히 '어림짐작'하는 휴리스틱 함수를 만들어 보자. 휴리스틱은 어림짐작이기 떄문에 최적해를 잘못 찾을 수가 있는데 이런 일을 막기 위해 과소평가하는 휴리스틱, 낙관적인 휴리스틱을 만들어야 한다( 휴리스틱의 반환 값이 항상 남은 최단 경로의 길이보다 작거나 같다)
### 단순한 휴리스틱 함수의 구현
#### 여기서 처음 사용할 휴리스틱 함수는 아직 방문하지 않은 도시들에 대해 인접한 간선 중 가장 짧은 간선의 길이를 더하는 것이다.
### 지나온 경로를 이용한 가지치기
#### 앞으로 남은 조각들의 비용을 휴리스틱을 이용해 예측하는 것이 아니라 지금까지 만든 부분 답을 검사해서 가지치기를 할 수 있다. 지금까지 구한 답이 최적해가 아니라면 앞으로 탐색해도 이 답은 최적해가 될 수 없다. TSP에서는 두 개의 인접한 도시를 골라서 이 둘의 순서를 바꿔 본 뒤, 경로가 짧아지면 탐색을 중단하는 가지치기를 한다.
### MST 휴리스틱을 이용한 가지치기의 구현
#### MST는 최소 스패닝 트리라고 하는데 현재 위치에서 시작해서 아직 방문하지 않은 정점들을 모두 방문하고, 시작점으로 돌아오는 최단 경로 또한 이 정점들을 모두 연결하는 스패닝 트리이다. 따라서 최소 스패닝 트리의 가중치의 합은 항상 최단 경로보다 작다.
``` c++
// 상호 배타적 집합 자료 구조를 구현한다.
struct DisjoinSet{
    //n개의 원소로 구성된 집합 자료 구조를 만든다.
    DisjoinSet(int n);
    //here가 포함된 집합의 대표를 반환한다.
    int find(int here);
    //a가 포함된 집합과 b가 포함된 집합을 합친다.
    bool merge(int a, int b);
}
// 모든 도시 간의 도로를 길이 순으로 정렬해 저장해 둔다.
vector<pair<double,pair<int,int>>> edges;
// here와 시작점, 아직 방문하지 않은 도시들을 모두 연결하는 MST를 찾는다.
double mstHeuristic(int here, const vector<bool>& visited) {
    DisjoinSet sets(n);
    double taken =0;
    for(int i=0; i<edges.size(); ++i) {
    int a = edges.[i].second.first, b = edges[i].second.second;
    if(a !=0 && a!= here && visited[a]) continue;
    if(b !=0 && b!= here && visited[b]) continue;
    if(sets.merge(a,b))
        taken += edges[i].first;
    }
    return taken;
}
```
### 마지막 단계 메모이제이션
#### 남은 조각의 수가 미리 정해둔 수 k 이하일때만 메모이제이션을 하도록 하자. 하지만 가지치기를 사용하는 함수는 쉽게 메모이제이션을 할 수 없다. 함수의 반환 값이 현재의 상태에만 영향을 받아야 하는데 가지치기는 반환 값이 달라질 수 있기 때문이다.
이 것을 주의해서 보자.
1. shortestPath2()가 사용하는 캐시는 배열이지만 dp()는 map을 사용한다.(배열을 쓰면 조합의 수가 너무 많기 때문)
2. shortestPath2()에서는 캐시가 2차원 배열이지만 dp는 3차원 배열이다( 현 위치, 남은 정점의 수)


429 내풀이 가장 길이가 긴 것을 뽑는다. 뽑은 것중에 없는 문자 ex> b가 없기 때문에 b를 가지고 있는 배열을 다 고른다. 둘이 합치고 또 없는 수를 고른다. 없는 문자가 없을 때 까지 한다.
or 긴 것을 뽑고 긴 것의 없는 수를 모두 나열. 없는 수를 가장 많이 포함하는 문자열 채택.둘이 합침 -> 이것을 재귀.
422 이문제는 꼭 L자 모양아닌가? n개의 크기 모양 아무거나? 그래서 11.7같은 코드가 되는것?
246 y,x 덮지않기로 결정한것을 막아두는 것 (이 칸에 놓을 수 없으면??)
409 도시를 번호 순서대로 방문~ 이거원래 가까운 것부터 방문하지 않음?? 번호순?
415 최소 스패닝트리의 가중치의 합은 항상 최단 경로보다 작다? 그게 똑같은거 아닌가

``` java
import java.util.Scanner;
public static void main(String[] args) {
    HashMap<String,String> map = new HashMap<String,String>();
    Scanner sc = new Scanner(System.in);
    int n;
    n = sc.nextInt();
    String word = new String[n];
    for(int i=0;i<n;i++)
        word[i] = sc.nextLine();
    map.put("a","as");
    map.put("i","ios");
    map.put("y","ios");
    map.put("l","les");
    map.put("n","anes");
    map.put("e","anes");
    map.put("o","os");
    map.put("r","res");
    map.put("t","tas");
    map.put("u","us");
    map.put("v","ves");
    map.put("w","was");
    string[] eng = {"a","i","y","l","n","e","o","r","t","u","v","w"};
    for(int i=0; i<n;i++){
       int len =word[i].length();
       for(int j=0;j<eng.length();j++)
           { if(word[i].substring(len-1,1)== eng[j])
               { if(word[i].substring(len-2,1)== "n")
                    {
                     System.out.println(word[i].replace(len-2,len-1,map.get(eng[j])));
                     continue;
                     }
                else{
                    System.out.println(word[i].replace(len-1,len-1,map.get(eng[j])));
                    continue;
                    }
                }
           }
         System.out.println(word[i].replace(len-1,len-1,"us");
         continue;
    }
}
```