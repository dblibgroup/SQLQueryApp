	public static void main(String[] args) {  
		try{
			Object rs[][] = null;             //rs is the set of all books retrieved according to the KEYWORD
			Object rsp[][] = null;            //rsp is every book's different positions
			Object rsi[][] = null;            //rsi is every book's cover
			int selection = 0; //0 represents book type, 1 represents book name, 2 represents author name, 3 represents press name
			String input = "文学";            //this is some user's input to find what he/her want
			String sql = null;
			String keyword = "%" + input + "%"; //String matched this pattern should be retrieved
		    PreparedStatement pstmt = null;
			try{
				//check if the user exists
				Query usrQuery = new Query();
				Connection conn = DbConnection.DbConnect();
				
				switch(selection)
				{
					case 0 :  //0 represents book type
						sql = "SELECT * FROM bookInfo WHERE type like ?";
						break;
					case 1 :  //1 represents book name
						sql = "SELECT * FROM bookInfo WHERE name like ?";
						break;
					case 2 :  //2 represents author name
						sql = "SELECT * FROM bookInfo WHERE author like ?";
						break;
					case 3 :  //3 represents press name
						sql = "SELECT * FROM bookInfo WHERE press like ?";
						break;
				}
				
		 	    pstmt = (PreparedStatement) conn.prepareStatement(sql,
		 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
		 	    		  ResultSet.CONCUR_READ_ONLY);       //It is not suggested to do so in large Result sets.
			    pstmt.setString(1, keyword);
				rs = usrQuery.PsExecQuery(pstmt);
				
				if(rs == null || rs.length == 0 || (rs.length == 1 && rs[0].length == 0)){
					System.out.println("无书籍查询结果！");
				}else{
					//ALL INFORMATION are listed below, and the PICTUREs of the books can be shown by their number
					System.out.println("ISBN 类别 书名 作者 出版社 简介 图片编号");
					for(int i = 0; i < rs.length; i++){
						for(int j = 0; j < rs[i].length; j++){
							if(j == 5)                          //Any introduction more than 20 chars will be cut
							{
								String substring = (String)rs[i][j];
								if(substring.length() >= 15)
								{
									substring = substring.substring(0, 14);
									substring += "...";
									rs[i][j] = substring;
								}
							}
							System.out.print(rs[i][j] + " ");
						}
						System.out.println("");
					}
					
					//Find out all the positions and numbers of the books from the array
					for(int i = 0; i < rs.length; i++){
						String posSql = "SELECT * FROM shelf_book WHERE ISBN = ?";
					    pstmt = null;
					    rsp = null;
				 	    pstmt = (PreparedStatement) conn.prepareStatement(posSql,
				 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
				 	    		  ResultSet.CONCUR_READ_ONLY);      
					    pstmt.setString(1, (String)rs[i][0]);                  //Find out all the positions according to the ISBN
						rsp = usrQuery.PsExecQuery(pstmt);
						if(rsp == null || rsp.length == 0 || (rsp.length == 1 && rsp[0].length == 0)){
							System.out.println("抱歉，该书籍尚未上架！");
						}else{
							for(int j = 0; j < rsp.length; j++){
								System.out.println("书架号：" + rsp[j][0] + "." + rsp[j][1] + " 上，ISBN为："+ rsp[j][2] + 
										" 的书数量为：" + rsp[j][3] + " 本");
							}
						}
					}
					
					//Find out all the image file's name of every book
					for(int i = 0; i < rs.length; i++){
						String posSql = "SELECT * FROM image WHERE imageNO = ?";
					    pstmt = null;
					    rsi = null;
				 	    pstmt = (PreparedStatement) conn.prepareStatement(posSql,
				 	    		  ResultSet.TYPE_SCROLL_INSENSITIVE, 
				 	    		  ResultSet.CONCUR_READ_ONLY);      
					    pstmt.setString(1, (String)rs[i][6]);                  //Find out all the positions according to the ISBN
					    rsi = usrQuery.PsExecQuery(pstmt);
						if(rsi == null || rsi.length == 0 || (rsi.length == 1 && rsi[0].length == 0)){
							System.out.println("该书暂未有封面图。");
						}else{
							for(int j = 0; j < rsi.length; j++){
								System.out.println("书名："+ rs[i][2] + " 图片名：" + rsi[j][1]);
							}
						}
					}
				}
			} catch (SQLException se){
			    se.printStackTrace();
		    }
		} catch(Exception e){
			e.printStackTrace();
		}
	}