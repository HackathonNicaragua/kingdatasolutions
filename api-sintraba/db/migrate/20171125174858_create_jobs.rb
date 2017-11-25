class CreateJobs < ActiveRecord::Migration[5.1]
  def change
    create_table :jobs do |t|
      t.string    "title",		limit: 100      
      t.string    "description",	limit: 140
      t.timestamps
    end
  end
end
