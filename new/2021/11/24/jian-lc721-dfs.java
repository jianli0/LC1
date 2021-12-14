class Solution {
    HashSet<String> visited = new HashSet<>();
    Map<String, List<String>> edges = new HashMap<String, List<String>>();
    
    private void dfs(List<String> mergedAccount, String email) {
        visited.add(email);
        
        mergedAccount.add(email);
        
        if (!edges.containsKey(email)) {
            return;
        }
        
        for (String neighbor : edges.get(email)) {
            if (!visited.contains(neighbor)) {
                dfs(mergedAccount, neighbor);
            }
        }
    }
    
    public List<List<String>> accountsMerge(List<List<String>> accountList) {
        List<List<String>> res = new ArrayList<>();
        
        if (accountList == null || accountList.size() == 0) {
            return res;
        }

        for (List<String> account : accountList) {    
            // Building adjacency list
            // Adding edge between first email to all other emails in the account
            String accountFirstEmail = account.get(1);
            for (int j = 2; j < account.size(); j++) {
                String accountEmail = account.get(j);
                // edge from first to other
                edges.putIfAbsent(accountFirstEmail, new ArrayList<String>());
                edges.get(accountFirstEmail).add(accountEmail);
                
                // edge from other to first
                edges.putIfAbsent(accountEmail, new ArrayList<String>());
                edges.get(accountEmail).add(accountFirstEmail);
            }   
        }
        
        
        // Traverse over all the accounts to store components
        for (List<String> account : accountList) {
            String accountName = account.get(0);
            String accountFirstEmail = account.get(1);
            
            // If email is visited, then it's a part of different component
            // Hence perform DFS only if email is not visited yet
            
            if (visited.contains(accountFirstEmail)) {
                continue;
            }
            
            List<String> mergedAccount = new ArrayList<>();
            dfs(mergedAccount, accountFirstEmail);
            Collections.sort(mergedAccount); 
            
            // Adding account name at the 0th index
            mergedAccount.add(0, accountName);
            res.add(mergedAccount);
        }
        
        return res;
    }
}
